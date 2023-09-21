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
import com.ntu.sw.expensestracker.exceptions.UserNotFoundException;
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
        logger.info("🟢 Creating new category for userId: " + userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User currentUser = optionalUser.get();
            category.setUser(currentUser);
            category.setCategoryNum(currentUser.getCategories().size() + 1);
            category.setCategoryName(category.getCategoryName().toLowerCase());
            // Prevent creation of category with the same name
            if (checkIfCategoryAlreadyExist(currentUser.getCategories(), category.getCategoryName())) {
                logger.warn("🟠 Failed to create new category for userId: " + userId);
                throw new CategoryAlreadyExist(category.getCategoryName());
            }
            Category newCategory = categoryRepository.save(category);
            logger.info("🟢 Created " + category.getCategoryName() + " for userId: " + userId);
            return newCategory;
        }
        logger.warn("🟠 Failed to create new category for userId: " + userId);
        throw new UserNotFoundException(userId);
    }

    @Override
    public List<Category> getAllCategory() {
        logger.info("🟢 Getting all categories...");
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getAllCategoryByUser(Long userId) {
        logger.info("🟢 Getting all categories for userId: " + userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User currentUser = optionalUser.get();
            return currentUser.getCategories();
        }
        logger.warn("🟠 Failed get all categories for userId: " + userId);
        throw new UserNotFoundException(userId);
    }

    @Override
    public Category updateCategory(Long userId, Long id, Category category) {
        logger.info("🟢 Updating category for userId: " + userId);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User currentUser = optionalUser.get();
            category.setUser(currentUser);
            category.setCategoryName(category.getCategoryName().toLowerCase());
            if (checkIfCategoryAlreadyExist(currentUser.getCategories(), category.getCategoryName())) {
                logger.warn("🟠 Failed to update category for userId: " + userId);
                throw new CategoryAlreadyExist(category.getCategoryName());
            }
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            if (optionalCategory.isPresent()) {
                Category categoryToUpdate = optionalCategory.get();
                categoryToUpdate.setCategoryName(category.getCategoryName().toLowerCase());
                logger.info("🟢 Succesfully updated "+ categoryToUpdate.getCategoryName() + " for userId: " + userId );
                return categoryRepository.save(categoryToUpdate);
            }
            logger.warn("🟠 Failed to update category for userId: " + userId);
            throw new CategoryNotFound(id);
        }
        logger.warn("🟠 Failed to update category for userId: " + userId);
        throw new UserNotFoundException(id);
    }

    @Override
    public void deleteCategory(Long userId, int categoryNum) {
        logger.info("🟢 Deleting categoryNum: " + categoryNum + " for userId: " + userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User currentUser = optionalUser.get();
            Category categoryToDelete = findCategoryByCategoryNum(currentUser.getCategories(), categoryNum);
            categoryRepository.deleteById(categoryToDelete.getId());
        } else {
            logger.warn("🟠 Failed to delete category for userId: " + userId);
            throw new UserNotFoundException(userId);
        }
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

    //Helper function to check if category exist 
    private Category findCategoryByCategoryNum(List<Category> categories, int categoryNum) {
        for (int i = 0; i < categories.size() ; i++) {
            if (categories.get(i).getCategoryNum() == categoryNum) {
                return categories.get(i);
            }
        }
        throw new CategoryNotFound(categoryNum);
    }


}