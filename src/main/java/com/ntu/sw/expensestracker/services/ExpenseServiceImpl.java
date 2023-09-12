package com.ntu.sw.expensestracker.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ntu.sw.expensestracker.entity.Expense;
import com.ntu.sw.expensestracker.exceptions.ExpenseNotFound;
import com.ntu.sw.expensestracker.repo.ExpenseRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseRepository expenseRepository;

    // CREATE
    @Override
    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    // READ ALL (GET ALL)
    @Override
    public List<Expense> getAllExpense() {
        return expenseRepository.findAll();
    }

    // READ ONE (GET ONE)
    @Override
    public Expense getExpense(Long id) {
        return expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFound(id));
    }

    // UPDATE
    @Override
    public Expense updateExpense(Long id, Expense expense) {
        Expense expenseToUpdate = expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFound(id));
        expenseToUpdate.setExpenseDate(expense.getExpenseDate());
        expenseToUpdate.setDescription(expense.getDescription());
        return expenseRepository.save(expenseToUpdate);

    }

    // DELETE
    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

}
