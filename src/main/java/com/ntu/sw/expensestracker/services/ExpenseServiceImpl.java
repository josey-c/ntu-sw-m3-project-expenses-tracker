package com.ntu.sw.expensestracker.services;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.ntu.sw.expensestracker.entity.Expense;
import com.ntu.sw.expensestracker.exceptions.ExpenseNotFound;
import com.ntu.sw.expensestracker.repo.ExpenseRepository;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Primary
@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseRepository expenseRepository;

    private final Logger logger = LoggerFactory.getLogger(ExpenseServiceImpl.class);

    // CREATE
    @Override
    public Expense createExpense(Expense expense) {
        logger.info("🟢 ExpenseServiceImpl.createExpense() called");
        return expenseRepository.save(expense);
    }

    // READ ALL (GET ALL)
    @Override
    public List<Expense> getAllExpense() {
        logger.info("🟢 ExpenseServiceImpl.getAllExpense() called");
        return expenseRepository.findAll();
    }

    // READ ONE (GET ONE)
    @Override
    public Expense getExpense(Long id) {
        logger.info("🟢 ExpenseServiceImpl.getExpense() called");
        return expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFound(id));
    }

    // UPDATE
    @Override
    public Expense updateExpense(Long id, Expense expense) {
        logger.info("🟢 ExpenseServiceImpl.updateExpense() called");
        Expense expenseToUpdate = expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFound(id));
        expenseToUpdate.setExpenseDate(expense.getExpenseDate());
        expenseToUpdate.setDescription(expense.getDescription());
        return expenseRepository.save(expenseToUpdate);

    }

    // DELETE
    @Override
    public void deleteExpense(Long id) {
        logger.info("🟢 ExpenseServiceImpl.deleteExpense() called");
        expenseRepository.deleteById(id);
    }

}
