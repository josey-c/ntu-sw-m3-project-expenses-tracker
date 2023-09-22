package com.ntu.sw.expensestracker.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ntu.sw.expensestracker.entity.Category;
import com.ntu.sw.expensestracker.entity.Expense;
import com.ntu.sw.expensestracker.entity.Wallet;
import com.ntu.sw.expensestracker.repo.CategoryRepository;
import com.ntu.sw.expensestracker.repo.ExpenseRepository;
import com.ntu.sw.expensestracker.repo.UserRepository;
import com.ntu.sw.expensestracker.repo.WalletRepository;

@Service
public class DeleteService {
    private ExpenseRepository expenseRepository;
    private WalletRepository walletRepository;
    private CategoryRepository categoryRepository;

    public DeleteService(ExpenseRepository expenseRepository, WalletRepository walletRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.walletRepository = walletRepository;
        this.categoryRepository = categoryRepository;
    }

    public void deleteAllExpenses(List<Expense> expenses) {
        for (Expense expense : expenses) {
            expenseRepository.deleteById(expense.getId());
        }
    }

    public void deleteAllCategories(List<Category> categories) {
        for (Category category : categories) {
            categoryRepository.deleteById(category.getId());
        }
    }

    public void deleteAllWallets(List<Wallet> wallets) {
        for (Wallet wallet : wallets) {
            deleteAllExpenses(wallet.getExpenses());
            walletRepository.deleteById(wallet.getWalletId());
        }
    }
    
}
