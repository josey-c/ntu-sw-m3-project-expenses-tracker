package com.ntu.sw.expensestracker.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ntu.sw.expensestracker.entity.User;
import com.ntu.sw.expensestracker.entity.Wallet;
import com.ntu.sw.expensestracker.exceptions.UserNotFoundException;
import com.ntu.sw.expensestracker.repo.UserRepository;
import com.ntu.sw.expensestracker.repo.WalletRepository;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private WalletRepository walletRepository;

    @Autowired
    public UserServiceImpl (UserRepository userRepository, WalletRepository walletRepository){
        this.userRepository= userRepository;
        this.walletRepository = walletRepository;
    }
    
    //create 1 user
    @Override
    public User createUser(User user){
        User newUser = userRepository.save(user);
        return newUser;
    }

    //get 1 user
    @Override
    public User getUser(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User foundUser = optionalUser.get();
            return foundUser;
        } else {
            throw new UserNotFoundException(id);
        }
    }

    //get all users
    @Override
     public ArrayList<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return (ArrayList<User>) allUsers;
    }

    //update user
    @Override
    public User updateUser(Long id, User user) {
        Optional<User> optionalUser= userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User userToUpdate = optionalUser.get();
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setEmail(user.getEmail());
            return userRepository.save(userToUpdate);
        }
        throw new UserNotFoundException(id);
    }

    //delete user
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    //add wallet to user
    @Override
    public Wallet addWalletToUser(long id, Wallet wallet){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User selectedUser = optionalUser.get();
            wallet.setUser(selectedUser);
            String walletName = wallet.getWalletName();
            wallet.setWalletName(walletName);
            return walletRepository.save(wallet);
        }
        throw new UserNotFoundException(id);
    }
}
