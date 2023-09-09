package com.ntu.sw.expensestracker.exceptions;

public class CategoryAlreadyExist extends RuntimeException {
    public CategoryAlreadyExist (String name) {
        super(name.toLowerCase() + " category already exist");
    }
}
