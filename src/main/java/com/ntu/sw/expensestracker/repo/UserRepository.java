package com.ntu.sw.expensestracker.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ntu.sw.expensestracker.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
