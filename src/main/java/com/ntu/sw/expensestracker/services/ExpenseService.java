package com.ntu.sw.expensestracker.services;

import java.util.List;

import com.ntu.sw.expensestracker.entity.Expense;

public interface ExpenseService {

    // CREATE
    Expense createExpense(Long userId, Long walletId, int categoryNum, Expense expense);

    // READ ALL (GET ALL)
    List<Expense> getAllExpense();

    List<Expense> getAllExpenseByWallet(Long userId, Long walletId);

    // READ ONE (GET ONE)
    Expense getExpense(Long id);

    // UPDATE
    Expense updateExpense(Long userId, Long walletId, Long id, Expense expense, int categoryNum);

    // DELETE
    void deleteExpense(Long userId, Long walletId, Long id);

}
