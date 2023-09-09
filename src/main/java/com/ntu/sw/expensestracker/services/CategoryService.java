package com.ntu.sw.expensestracker.services;

import java.util.List;

import com.ntu.sw.expensestracker.entity.Category;

public interface CategoryService {
    Category createCategory(Category category);
    List<Category> getAllCategory();
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);
}
