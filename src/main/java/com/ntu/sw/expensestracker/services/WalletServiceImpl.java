package com.ntu.sw.expensestracker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ntu.sw.expensestracker.entity.Expense;
import com.ntu.sw.expensestracker.entity.User;
import com.ntu.sw.expensestracker.entity.Wallet;
import com.ntu.sw.expensestracker.exceptions.UserNotFoundException;
import com.ntu.sw.expensestracker.exceptions.WalletAlreadyExist;
import com.ntu.sw.expensestracker.exceptions.WalletNotFoundException;
import com.ntu.sw.expensestracker.repo.ExpenseRepository;
import com.ntu.sw.expensestracker.repo.UserRepository;
import com.ntu.sw.expensestracker.repo.WalletRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WalletServiceImpl implements WalletService {

    // constructor inject walletRepository
    private WalletRepository walletRepository;
    private UserRepository userRepository;
    private ExpenseRepository expenseRepository;

    private final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository, UserRepository userRepository,
            ExpenseRepository expenseRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    // CREATE 1 wallet
    @Override
    public Wallet createWallet(Long userId, Wallet wallet) {

        // check if user if present before adding wallet
        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            logger.error("🔴 WalletServiceImpl.createWallet() failed");
            throw new UserNotFoundException(userId);
        }

        User currentUser = userRepository.findById(userId).get();
        logger.info("🟢 WalletServiceImpl.createWallet() called");
        wallet.setUser(currentUser);
        wallet.setWalletName(wallet.getWalletName().toLowerCase());
        if (checkIfWalletAlreadyExist(currentUser.getWallets(), wallet.getWalletName())) {
            logger.error("🔴 WalletServiceImpl.createWallet() failed");
            throw new WalletAlreadyExist(wallet.getWalletName());
        }
        Wallet newWallet = walletRepository.save(wallet);
        return newWallet;
    }

    // GET all wallet
    @Override
    public List<Wallet> getAllWallet() {
        logger.info("🟢 WalletServiceImpl.getAllWallet() called");
        return walletRepository.findAll();
    }

    // GET wallet of 1 user
    @Override
    public List<Wallet> getAllWalletByUser(Long userId) {
        User currentUser = userRepository.findById(userId).get();
        logger.info("🟢 WalletServiceImpl.getAllWalletByUser() called");
        return currentUser.getWallets();
    }

    // UPDATE wallet
    @Override
    public Wallet updateWallet(Long userId, Long id, Wallet wallet) {
        User currentUser = userRepository.findById(userId).get();
        wallet.setUser(currentUser);
        wallet.setWalletName(wallet.getWalletName().toLowerCase());
        if (checkIfWalletAlreadyExist(currentUser.getWallets(), wallet.getWalletName())) {
            logger.error("🔴 WalletServiceImpl.createWallet() failed");
            throw new WalletAlreadyExist(wallet.getWalletName());
        }
        Optional<Wallet> optionalWallet = walletRepository.findById(id);
        if (optionalWallet.isPresent()) {
            logger.info("🟢 WalletServiceImpl.updateWallet() called");
            Wallet walletToUpdate = optionalWallet.get();
            walletToUpdate.setWalletName(wallet.getWalletName().toLowerCase());
            return walletRepository.save(walletToUpdate);
        }
        logger.error("🔴 WalletServiceImpl.createWallet() failed");
        throw new WalletNotFoundException(id);
    }

    @Override
    public void deleteWallet(Long id) {
        logger.info("🟢 WalletServiceImpl.deleteWallet() called");
        walletRepository.deleteById(id);
    }

    // Other methods
    private boolean checkIfWalletAlreadyExist(List<Wallet> wallets, String walletName) {
        for (int i = 0; i < wallets.size(); i++) {
            if (wallets.get(i).getWalletName().equals(walletName)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    // Add expense to wallet
    @Override
    public Expense addExpenseToWallet(Long id, Expense expense) {
        Optional<Wallet> optionalWallet = walletRepository.findById(id);
        if (optionalWallet.isPresent()) {
            logger.info("🟢 WalletServiceImpl.addExpenseToWallet() called");
            Wallet selectedWallet = optionalWallet.get();
            expense.setWallet(selectedWallet);
            return expenseRepository.save(expense);
        }
        logger.info("🔴 WalletServiceImpl.addExpenseToWallet() failed");
        throw new WalletNotFoundException(id);
    }
}