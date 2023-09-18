package com.ntu.sw.expensestracker.exceptions;

public class WalletAlreadyExist extends RuntimeException {
    public WalletAlreadyExist (String name) {
        super(name.toLowerCase() + " wallet already exist");
    }
}