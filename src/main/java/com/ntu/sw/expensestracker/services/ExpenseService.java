package com.ntu.sw.expensestracker.services;

import java.util.List;

import com.ntu.sw.expensestracker.entity.Expense;

public interface ExpenseService {

    // CREATE
    Expense createExpense(Long userId, Long walletId, Expense expense);

    // READ ALL (GET ALL)
    List<Expense> getAllExpense();

    List<Expense> getAllExpenseByWallet(Long walletId);

    // READ ONE (GET ONE)
    Expense getExpense(Long id);

    // UPDATE
    Expense updateExpense(Long walletId, Long id, Expense expense);

    // DELETE
    void deleteExpense(Long id);

}
