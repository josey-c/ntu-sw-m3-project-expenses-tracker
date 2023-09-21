package com.ntu.sw.expensestracker.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ntu.sw.expensestracker.entity.Expense;
import com.ntu.sw.expensestracker.entity.Wallet;
import com.ntu.sw.expensestracker.services.WalletService;

@RestController
public class WalletController {
    private WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("users/{userId}/wallets")
    public ResponseEntity<Wallet> createWallet(@PathVariable Long userId, @Valid @RequestBody Wallet wallet) {       
        Wallet newWallet = walletService.createWallet(userId, wallet);
        return new ResponseEntity<>(newWallet, HttpStatus.CREATED);
    }

    @GetMapping("/wallets")
    public ResponseEntity<List<Wallet>> getAllWallet() {
        List<Wallet> allWallet = walletService.getAllWallet();
        return new ResponseEntity<>(allWallet, HttpStatus.OK);
    }

    @GetMapping("users/{userId}/wallets")
    public ResponseEntity<List<Wallet>> getAllWalletByUser(@PathVariable Long userId) {
        List<Wallet> allWalletByUser = walletService.getAllWalletByUser(userId);
        return new ResponseEntity<>(allWalletByUser, HttpStatus.OK);
    }

    @PutMapping("users/{userId}/wallets/{id}")
    public ResponseEntity<Wallet> editWallet(@PathVariable Long userId, @PathVariable Long id,
            @RequestBody Wallet wallet) {
        Wallet editWallet = walletService.updateWallet(userId, id, wallet);
        return new ResponseEntity<Wallet>(editWallet, HttpStatus.OK);
    }

    @DeleteMapping("/wallets/{id}")
    public ResponseEntity<HttpStatus> deleteWallet(@PathVariable Long id) {
        walletService.deleteWallet(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    // add expense to wallet
    @PostMapping("/wallets/{id}/expense")
    public ResponseEntity<Expense> addExpenseToWallet(@PathVariable Long id, Expense expense) {
        Expense newExpense = walletService.addExpenseToWallet(id, expense);
        return new ResponseEntity<>(newExpense, HttpStatus.OK);
    }

}
