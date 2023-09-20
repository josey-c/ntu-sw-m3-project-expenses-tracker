package com.ntu.sw.expensestracker.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.ntu.sw.expensestracker.entity.Category;
import com.ntu.sw.expensestracker.entity.User;
import com.ntu.sw.expensestracker.entity.Wallet;
import com.ntu.sw.expensestracker.exceptions.UserNotFoundException;
import com.ntu.sw.expensestracker.repo.CategoryRepository;
import com.ntu.sw.expensestracker.repo.UserRepository;
import com.ntu.sw.expensestracker.repo.WalletRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Primary
@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private WalletRepository walletRepository;
    private CategoryRepository categoryRepository;


    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    public UserServiceImpl (UserRepository userRepository, WalletRepository walletRepository, CategoryRepository categoryRepository){
        this.userRepository= userRepository;
        this.walletRepository = walletRepository;
        this.categoryRepository = categoryRepository;
    }
    
    //create 1 user
    @Override
    public User createUser(User user){
        logger.info("🟢 UserServiceImpl.createUser() called");
        User newUser = userRepository.save(user);


        // Add a main wallet to newly created user
        Wallet main = new Wallet("main", user);
        walletRepository.save(main);
        List<Wallet> wallets = Arrays.asList(main);
        newUser.setWallets(wallets);

        // Add a few categories to newly created user 
        Category food = new Category("food", 1, user);
        Category transport = new Category("transport", 2, user);
        Category bills = new Category("bills", 3, user);
        categoryRepository.save(food);
        categoryRepository.save(transport);
        categoryRepository.save(bills);
        List<Category> categories = Arrays.asList(food, transport, bills);
        newUser.setCategories(categories);

        return newUser;
    }

    //get 1 user
    @Override
    public User getUser(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            logger.info("🟢 UserServiceImpl.getUser() called");
            User foundUser = optionalUser.get();
            return foundUser;
        } else {
            logger.error("🔴 UserServiceImpl.getUser() failed");
            throw new UserNotFoundException(id);
        }
    }

    //get all users
    @Override
     public ArrayList<User> getAllUsers() {
        logger.info("🟢 UserServiceImpl.getAllUsers() called");
        List<User> allUsers = userRepository.findAll();
        return (ArrayList<User>) allUsers;
    }

    //update user
    @Override
    public User updateUser(Long id, User user) {
        Optional<User> optionalUser= userRepository.findById(id);
        if (optionalUser.isPresent()) {
            logger.info("🟢 UserServiceImpl.updateUser() called");
            User userToUpdate = optionalUser.get();
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setEmail(user.getEmail());
            return userRepository.save(userToUpdate);
        }
        logger.error("🔴 UserServiceImpl.updateUser() failed");
        throw new UserNotFoundException(id);
    }

    //delete user
    @Override
    public void deleteById(Long id) {
        logger.info("🟢 UserServiceImpl.deleteById() called");
        userRepository.deleteById(id);
    }

    //add wallet to user
    @Override
    public Wallet addWalletToUser(long id, Wallet wallet){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            logger.info("🟢 UserServiceImpl.addWalletToUser() called");
            User selectedUser = optionalUser.get();
            wallet.setUser(selectedUser);
            String walletName = wallet.getWalletName();
            wallet.setWalletName(walletName);
            return walletRepository.save(wallet);
        }
        logger.info("🔴 UserServiceImpl.addWalletToUser() failed");
        throw new UserNotFoundException(id);
    }
    
}