package com.ntu.sw.expensestracker.services;

import com.ntu.sw.expensestracker.entity.User;
import com.ntu.sw.expensestracker.entity.Wallet;

import java.util.ArrayList;

public interface UserService {
    User createUser(User user);
    User getUser(Long id);
    ArrayList<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteById(Long id); 
    Wallet addWalletToUser(long id, Wallet wallet);
}
