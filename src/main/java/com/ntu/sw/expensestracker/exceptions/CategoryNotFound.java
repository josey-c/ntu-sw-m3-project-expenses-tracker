package com.ntu.sw.expensestracker.exceptions;

public class CategoryNotFound extends RuntimeException {
    public CategoryNotFound(Long id) {
        super("Category with Id: " + id + " does not exist");
    }

    public CategoryNotFound(int id) {
        super("Category with categoryNum: " + id + " does not exist");
    }
}
