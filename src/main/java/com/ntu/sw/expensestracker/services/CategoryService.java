package com.ntu.sw.expensestracker.services;

import java.util.List;

import com.ntu.sw.expensestracker.entity.Category;

public interface CategoryService {
    Category createCategory(Long userId, Category category);
    List<Category> getAllCategory();
    List<Category> getAllCategoryByUser(Long userId);
    Category updateCategory(Long userId, Long id, Category category);
    void deleteCategory(Long userId, int categoryNum);
}
