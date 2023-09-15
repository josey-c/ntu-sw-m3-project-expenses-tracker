package com.ntu.sw.expensestracker.services;

import java.util.ArrayList;

import com.ntu.sw.expensestracker.entity.Wallet;

public interface WalletService {

    Wallet createWallet(Wallet wallet); 
    Wallet getWallet(Long id); 
    ArrayList<Wallet> getAllWallets();
    Wallet updateWallet(Long id, Wallet wallet); 
    void deleteById(Long id); 
    
    
}
