package com.ntu.sw.expensestracker.exceptions;

public class WalletNotFoundException extends RuntimeException{
    public WalletNotFoundException(Long id) {
        super("Could not find wallet with id: " + id);
    }
}
