package com.ntu.sw.expensestracker.exceptions;

public class ExpenseNotFound extends RuntimeException {
    public ExpenseNotFound(Long id) {
        super("Could not find expense with id: " + id);
    }

}
