package com.ntu.sw.expensestracker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ntu.sw.expensestracker.entity.Category;
import com.ntu.sw.expensestracker.services.CategoryService;

@RestController
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController (CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("users/{userId}/categories") 
    public ResponseEntity<Category> createCategory(@PathVariable Long userId, @RequestBody Category category) {
        Category newCategory = categoryService.createCategory(userId, category);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }


    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategory() {
        List<Category> allCategory = categoryService.getAllCategory();
        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }

    @GetMapping("users/{userId}/categories")
    public ResponseEntity<List<Category>> getAllCategoryByUser(@PathVariable Long userId) {
        List<Category> allCategoryByUser = categoryService.getAllCategoryByUser(userId);
        return new ResponseEntity<>(allCategoryByUser, HttpStatus.OK);
    }

    @PutMapping("users/{userId}/categories/{id}")
    public ResponseEntity<Category> editCategory(@PathVariable Long userId, @PathVariable Long id, @RequestBody Category category) {
        Category editCategory = categoryService.updateCategory(userId, id, category);
        return new ResponseEntity<Category>(editCategory, HttpStatus.OK);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

}
