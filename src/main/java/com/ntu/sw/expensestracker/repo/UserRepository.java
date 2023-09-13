package com.ntu.sw.expensestracker.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ntu.sw.expensestracker.entity.Category;
import com.ntu.sw.expensestracker.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
