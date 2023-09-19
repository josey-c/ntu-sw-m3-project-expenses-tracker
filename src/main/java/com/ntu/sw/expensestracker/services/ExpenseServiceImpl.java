package com.ntu.sw.expensestracker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.ntu.sw.expensestracker.entity.Expense;
import com.ntu.sw.expensestracker.entity.User;
import com.ntu.sw.expensestracker.entity.Wallet;
import com.ntu.sw.expensestracker.exceptions.ExpenseNotFound;
import com.ntu.sw.expensestracker.exceptions.WalletNotFoundException;
import com.ntu.sw.expensestracker.repo.ExpenseRepository;
import com.ntu.sw.expensestracker.repo.UserRepository;
import com.ntu.sw.expensestracker.repo.WalletRepository;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Primary
@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseRepository expenseRepository;
    private WalletRepository walletRepository;
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(ExpenseServiceImpl.class);

    // CREATE
    @Override
    public Expense createExpense(Long userId, Long walletId, Expense expense) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);

        if (optionalWallet.isPresent()) {
            Wallet currentWallet = walletRepository.findById(walletId).get();
            expense.setWallet(currentWallet);
            logger.info("🟢 ExpenseServiceImpl.createExpense() called");
            return expenseRepository.save(expense);
        }
        logger.error("🔴 Wallet not found");
        throw new WalletNotFoundException(walletId);
    }

    // READ ALL (GET ALL)
    @Override
    public List<Expense> getAllExpense() {
        logger.info("🟢 ExpenseServiceImpl.getAllExpense() called");
        return expenseRepository.findAll();
    }

    // READ ALL (GET ALL) - by per wallet
    @Override
    public List<Expense> getAllExpenseByWallet(Long walletId) {
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);

        if (optionalWallet.isPresent()) {
            Wallet currentWallet = walletRepository.findById(walletId).get();
            logger.info("🟢 ExpenseServiceImpl.getAllExpenseByWallet() called");
            return currentWallet.getExpense();
        }
        logger.error("🔴 Wallet not found");
        throw new WalletNotFoundException(walletId);
    }

    // READ ONE (GET ONE)
    @Override
    public Expense getExpense(Long id) {
        logger.info("🟢 ExpenseServiceImpl.getExpense() called");
        return expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFound(id));
    }

    // UPDATE
    @Override
    public Expense updateExpense(Long walletId, Long id, Expense expense) {

        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        if (optionalWallet.isPresent()) {
            logger.info("🟢 ExpenseServiceImpl.updateExpense() called");
            Expense expenseToUpdate = expenseRepository.findById(id)
                    .orElseThrow(() -> new ExpenseNotFound(id));
            expenseToUpdate.setExpenseDate(expense.getExpenseDate());
            expenseToUpdate.setDescription(expense.getDescription());
            expenseToUpdate.setAmount(expense.getAmount());
            expenseToUpdate.setWallet(expense.getWallet());
            return expenseRepository.save(expenseToUpdate);
        }
        logger.error("🔴 Wallet not found");
        throw new WalletNotFoundException(walletId);
    }

    // DELETE
    @Override
    public void deleteExpense(Long id) {
        logger.info("🟢 ExpenseServiceImpl.deleteExpense() called");
        expenseRepository.deleteById(id);
    }

}
