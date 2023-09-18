package com.ntu.sw.expensestracker.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.ntu.sw.expensestracker.entity.User;
import com.ntu.sw.expensestracker.exceptions.UserNotFoundException;
import com.ntu.sw.expensestracker.repo.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Primary
@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    public UserServiceImpl (UserRepository userRepository){
        this.userRepository= userRepository;
    }
    
    //create 1 user
    @Override
    public User createUser(User user){
        logger.info("🟢 CustomerServiceImpl.createUser() called");
        User newUser = userRepository.save(user);
        return newUser;
    }

    //get 1 user
    @Override
    public User getUser(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            logger.info("🟢 CustomerServiceImpl.getUser() called");
            User foundUser = optionalUser.get();
            return foundUser;
        } else {
            logger.error("🔴 CustomerServiceImpl.getUser() failed");
            throw new UserNotFoundException(id);
        }
    }

    //get all users
    @Override
     public ArrayList<User> getAllUsers() {
        logger.info("🟢 CustomerServiceImpl.getAllUsers() called");
        List<User> allUsers = userRepository.findAll();
        return (ArrayList<User>) allUsers;
    }

    //update user
    @Override
    public User updateUser(Long id, User user) {
        Optional<User> optionalUser= userRepository.findById(id);
        if (optionalUser.isPresent()) {
            logger.info("🟢 CustomerServiceImpl.updateUser() called");
            User userToUpdate = optionalUser.get();
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setEmail(user.getEmail());
            return userRepository.save(userToUpdate);
        }
        logger.error("🔴 CustomerServiceImpl.updateUser() failed");
        throw new UserNotFoundException(id);
    }

    //delete user
    @Override
    public void deleteById(Long id) {
        logger.info("🟢 CustomerServiceImpl.deleteById() called");
        userRepository.deleteById(id);
    }
    
}
