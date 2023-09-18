package com.ntu.sw.expensestracker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.ntu.sw.expensestracker.entity.Category;
import com.ntu.sw.expensestracker.exceptions.CategoryAlreadyExist;
import com.ntu.sw.expensestracker.exceptions.CategoryNotFound;
import com.ntu.sw.expensestracker.repo.CategoryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Primary
@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);


    @Autowired
    public CategoryServiceImpl (CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(Category category) {
        // Prevent creation of category with the same name
        List<Category> result = categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName());
        if (result.isEmpty()) {
            logger.info("🟢 CategoryServiceImpl.createCategory() called");
            Category newCategory = categoryRepository.save(category);
            return newCategory;
        }
        logger.info("🔴 CategoryServiceImpl.createCategory() failed to call");
        throw new CategoryAlreadyExist(category.getCategoryName());
    }

    @Override
    public List<Category> getAllCategory() {
        logger.info("🟢 CategoryServiceImpl.getAllCategory() called");
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        // Prevent updating of a category to have a same name with an existing category
        List<Category> result = categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName());
        if (result.isEmpty()) {
            logger.info("🟢 CategoryServiceImpl.updateCategory() called");
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            if (optionalCategory.isPresent()) {
                Category categoryToUpdate = optionalCategory.get();
                categoryToUpdate.setCategoryName(category.getCategoryName().toLowerCase());
                return categoryRepository.save(categoryToUpdate);
            }
            throw new CategoryNotFound(id);
        }
        logger.info("🔴 CategoryServiceImpl.updateCategory() failed");
        throw new CategoryAlreadyExist(category.getCategoryName());
    }

    @Override
    public void deleteCategory(Long id) {
        logger.info("🟢 CategoryServiceImpl.deleteCategory() called");
        categoryRepository.deleteById(id);
    }
    
}
