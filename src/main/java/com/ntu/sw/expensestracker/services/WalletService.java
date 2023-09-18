package com.ntu.sw.expensestracker.services;

import java.util.List;

import com.ntu.sw.expensestracker.entity.Wallet;

public interface WalletService {
    Wallet createWallet(Long userId, Wallet wallet);
    List<Wallet> getAllWallet();
    List<Wallet> getAllWalletByUser(Long userId);
    Wallet updateWallet(Long userId, Long id, Wallet wallet);
    void deleteWallet(Long id);
}