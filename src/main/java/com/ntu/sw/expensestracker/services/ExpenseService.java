package com.ntu.sw.expensestracker.services;

import java.util.List;

import com.ntu.sw.expensestracker.entity.Expense;

public interface ExpenseService {

    // CREATE
    Expense createExpense(Expense expense);

    // READ ALL (GET ALL)
    List<Expense> getAllExpense();

    // READ ONE (GET ONE)
    Expense getExpense(Long id);

    // UPDATE
    Expense updateExpense(Long id, Expense expense);

    // DELETE
    void deleteExpense(Long id);

}
