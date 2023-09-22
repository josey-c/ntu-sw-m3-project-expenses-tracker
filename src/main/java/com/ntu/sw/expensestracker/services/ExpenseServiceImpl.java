package com.ntu.sw.expensestracker.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.ntu.sw.expensestracker.entity.Category;
import com.ntu.sw.expensestracker.entity.Expense;
import com.ntu.sw.expensestracker.entity.User;
import com.ntu.sw.expensestracker.entity.Wallet;
import com.ntu.sw.expensestracker.exceptions.CategoryNotFound;
import com.ntu.sw.expensestracker.exceptions.ExpenseNotFound;
import com.ntu.sw.expensestracker.exceptions.UserNotFoundException;
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
    public Expense createExpense(Long userId, Long walletId, int categoryNum, Expense expense) {
        logger.info("🟢 Creating expense for userId: " + userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        if (optionalUser.isPresent()) {
            User currentUser = optionalUser.get();
            Category category = findCategoryByCategoryNum(currentUser.getCategories(), categoryNum);
            if (optionalWallet.isPresent()) {
                Wallet currentWallet = CheckIfWalletIsUnderUser(currentUser.getWallets(), walletId);
                expense.setWallet(currentWallet);
                expense.setCategory(category);
                logger.info("🟢 Successfuly created " + expense.getDescription() + " expense for wallet " + currentWallet.getWalletName() + " under userId: " + userId);
                return expenseRepository.save(expense);
            }
            logger.warn("🟠 Failed to create expense for userId: " + userId);
            throw new WalletNotFoundException(walletId);
        }
        logger.warn("🟠 Failed to create expense for userId: " + userId);
        throw new UserNotFoundException(userId);
    }

    // READ ALL (GET ALL)
    @Override
    public List<Expense> getAllExpense() {
        logger.info("🟢 Getting all expenses...");
        return expenseRepository.findAll();
    }

    // READ ALL (GET ALL) - by per wallet
    @Override
    public List<Expense> getAllExpenseByWallet(Long userId, Long walletId) {
        logger.info("🟢 Getting all expenses for userId: " + userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        if (optionalUser.isPresent()) {
            User currentUser = optionalUser.get();
            if (optionalWallet.isPresent()) {
                Wallet currentWallet = CheckIfWalletIsUnderUser(currentUser.getWallets(), walletId);
                logger.info("🟢 Successfully get all expense for wallet " + currentWallet.getWalletName() + " under userId: " + userId);
                return currentWallet.getExpenses();
            }
            logger.warn("🟠 Failed to get all expenses for userId: " + userId);
            throw new WalletNotFoundException(walletId);
        }
        logger.warn("🟠 Failed to get all expenses for userId: " + userId);
        throw new UserNotFoundException(userId);
    }

    // READ ONE (GET ONE)
    @Override
    public Expense getExpense(Long id) {
        logger.info("🟢 Getting expenses with id: " + id);
        return expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFound(id));
    }

    // UPDATE
    @Override
    public Expense updateExpense(Long userId, Long walletId, Long id, Expense expense, int categoryNum) {
        logger.info("🟢 Updating " + expense.getDescription() + " expense for userId: " + userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        if (optionalUser.isPresent()) {
            User currentUser = optionalUser.get();
            Category category = findCategoryByCategoryNum(currentUser.getCategories(), categoryNum);
            if (optionalWallet.isPresent()) {
                Wallet currentWallet = CheckIfWalletIsUnderUser(currentUser.getWallets(), walletId);
                expense.setWallet(currentWallet);
                expense.setCategory(category);
                Optional<Expense> optionalExpense = expenseRepository.findById(id);
                if (optionalExpense.isPresent()) {
                    Expense expenseToUpdate = optionalExpense.get();
                    expenseToUpdate.setExpenseDate(expense.getExpenseDate());
                    expenseToUpdate.setDescription(expense.getDescription());
                    expenseToUpdate.setAmount(expense.getAmount());
                    expenseToUpdate.setWallet(expense.getWallet());
                    expenseToUpdate.setCategory(expense.getCategory());
                    logger.info("🟢 Successfully updated " + expenseToUpdate.getDescription() + " expense for wallet " + currentWallet.getWalletName() + " under userId: " + userId);
                    return expenseRepository.save(expenseToUpdate);
                }
                logger.warn("🟠 Failed to update " + expense.getDescription() + " expense for userId: " + userId);
                throw new ExpenseNotFound(id);
            }
            logger.warn("🟠 Failed to update " + expense.getDescription() + " expense for userId: " + userId);
            throw new WalletNotFoundException(walletId);
        }
        logger.warn("🟠 Failed to update " + expense.getDescription() + " expense for userId: " + userId);
        throw new UserNotFoundException(userId);
    }

    // DELETE
    @Override
    // public void deleteExpense(Long id) {
    //     logger.info("🟢 Deleting expense with Id: " + id);
    //     expenseRepository.deleteById(id);
    // }
    public void deleteExpense(Long userId, Long walletId, Long id) {
        logger.info("🟢 Deleting expense with Id: " + id + " for userId: " + userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User currentUser = optionalUser.get();
            Wallet currentWallet = CheckIfWalletIsUnderUser(currentUser.getWallets(), walletId);
            Optional<Expense> optionalExpense = expenseRepository.findById(id);
            if (optionalExpense.isPresent()) {
                Expense expenseToDelete = optionalExpense.get();
                if (expenseToDelete.getWallet().equals(currentWallet)) {
                    expenseRepository.deleteById(id);
                }
            }
            throw new ExpenseNotFound(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    //Helper function to check if category exist 
    private Category findCategoryByCategoryNum(List<Category> categories, int categoryNum) {
        for (int i = 0; i < categories.size() ; i++) {
            if (categories.get(i).getCategoryNum() == categoryNum) {
                return categories.get(i);
            }
        }
        throw new CategoryNotFound(categoryNum);
    }

    //Helper function to find wallet by Id
    private Wallet CheckIfWalletIsUnderUser(List<Wallet> wallets, Long walletId) {
        for (int i = 0; i < wallets.size() ; i++) {
            if (wallets.get(i).getWalletId() == walletId) {
                return wallets.get(i);
            }
        }
        throw new WalletNotFoundException(walletId);   
    }
}
