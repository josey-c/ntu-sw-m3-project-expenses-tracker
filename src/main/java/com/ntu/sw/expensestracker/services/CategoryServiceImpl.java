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

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    @Autowired
    public CategoryServiceImpl (CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Category createCategory(Long userId, Category category) {
        User currentUser = userRepository.findById(userId).get();
        category.setUser(currentUser);
        category.setCategoryNum(currentUser.getCategories().size()+1);
        category.setCategoryName(category.getCategoryName().toLowerCase());
        // Prevent creation of category with the same name
        if (checkIfCategoryAlreadyExist(currentUser.getCategories(), category.getCategoryName())) {
            throw new CategoryAlreadyExist(category.getCategoryName());
        }
        Category newCategory = categoryRepository.save(category);
        return newCategory; 

        // List<Category> result = categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName());
        // if (result.isEmpty()) {
        //     Category newCategory = categoryRepository.save(category);
        //     return newCategory;
        // }
        // throw new CategoryAlreadyExist(category.getCategoryName());
    }

    @Override
    public List<Category> getAllCategory() {
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
        category.setUser(currentUser);
        category.setCategoryName(category.getCategoryName().toLowerCase());
        if (checkIfCategoryAlreadyExist(currentUser.getCategories(), category.getCategoryName())) {
            throw new CategoryAlreadyExist(category.getCategoryName());
        }
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category categoryToUpdate = optionalCategory.get();
            categoryToUpdate.setCategoryName(category.getCategoryName().toLowerCase());
            return categoryRepository.save(categoryToUpdate);
        }
        throw new CategoryNotFound(id);

        // Prevent updating of a category to have a same name with an existing category
        // List<Category> result = categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName());
        // if (result.isEmpty()) {
        //     Optional<Category> optionalCategory = categoryRepository.findById(id);
        //     if (optionalCategory.isPresent()) {
        //         Category categoryToUpdate = optionalCategory.get();
        //         categoryToUpdate.setCategoryName(category.getCategoryName().toLowerCase());
        //         return categoryRepository.save(categoryToUpdate);
        //     }
        //     throw new CategoryNotFound(id);
        // }
        // throw new CategoryAlreadyExist(category.getCategoryName());
    }

    @Override
    public void deleteCategory(Long id) {
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
