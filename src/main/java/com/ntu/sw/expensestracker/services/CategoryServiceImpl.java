package com.ntu.sw.expensestracker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.ntu.sw.expensestracker.entity.Category;
import com.ntu.sw.expensestracker.entity.User;
import com.ntu.sw.expensestracker.exceptions.CategoryAlreadyExist;
import com.ntu.sw.expensestracker.exceptions.CategoryNotFound;
import com.ntu.sw.expensestracker.repo.CategoryRepository;
import com.ntu.sw.expensestracker.repo.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Primary
@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Category createCategory(Long userId, Category category) {
        logger.info("🟢 CategoryServiceImpl.createCategory() called");
        User currentUser = userRepository.findById(userId).get();
        category.setUser(currentUser);
        category.setCategoryNum(currentUser.getCategories().size() + 1);
        category.setCategoryName(category.getCategoryName().toLowerCase());
        // Prevent creation of category with the same name
        if (checkIfCategoryAlreadyExist(currentUser.getCategories(), category.getCategoryName())) {
            logger.info("🔴 CategoryServiceImpl.createCategory() failed to call");
            throw new CategoryAlreadyExist(category.getCategoryName());
        }
        Category newCategory = categoryRepository.save(category);
        return newCategory;
    }

    @Override
    public List<Category> getAllCategory() {
        logger.info("🟢 CategoryServiceImpl.getAllCategory() called");
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getAllCategoryByUser(Long userId) {
        User currentUser = userRepository.findById(userId).get();
        return currentUser.getCategories();
    }

    @Override
    public Category updateCategory(Long userId, Long id, Category category) {
        User currentUser = userRepository.findById(userId).get();
        logger.info("🟢 CategoryServiceImpl.updateCategory() called");

        category.setUser(currentUser);
        category.setCategoryName(category.getCategoryName().toLowerCase());
        if (checkIfCategoryAlreadyExist(currentUser.getCategories(), category.getCategoryName())) {
            throw new CategoryAlreadyExist(category.getCategoryName());
        }
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category categoryToUpdate = optionalCategory.get();
            categoryToUpdate.setCategoryName(category.getCategoryName().toLowerCase());
            logger.error("🔴 CategoryServiceImpl.updateCategory() failed");

            return categoryRepository.save(categoryToUpdate);

        }
        logger.error("🔴 CategoryServiceImpl.updateCategory() failed");

        throw new CategoryNotFound(id);
    }

    @Override
    public void deleteCategory(Long id) {
        logger.info("🟢 CategoryServiceImpl.deleteCategory() called");
        categoryRepository.deleteById(id);
    }

     // Helper function to check if categoryName exist in List of category objects
     private boolean checkIfCategoryAlreadyExist(List<Category> categories, String categoryName) {
        for (int i = 0; i < categories.size() ; i++) {
            if (categories.get(i).getCategoryName().equals(categoryName)) {
                return true;
            }
        }
        return false;
    }

}