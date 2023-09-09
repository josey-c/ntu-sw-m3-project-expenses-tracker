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

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl (CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(Category category) {
        // Prevent creation of category with the same name
        List<Category> result = categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName());
        if (result.isEmpty()) {
            Category newCategory = categoryRepository.save(category);
            return newCategory;
        }
        throw new CategoryAlreadyExist(category.getCategoryName());
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        // Prevent updating of a category to have a same name with an existing category
        List<Category> result = categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName());
        if (result.isEmpty()) {
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            if (optionalCategory.isPresent()) {
                Category categoryToUpdate = optionalCategory.get();
                categoryToUpdate.setCategoryName(category.getCategoryName().toLowerCase());
                return categoryRepository.save(categoryToUpdate);
            }
            throw new CategoryNotFound(id);
        }
        throw new CategoryAlreadyExist(category.getCategoryName());
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
    
}
