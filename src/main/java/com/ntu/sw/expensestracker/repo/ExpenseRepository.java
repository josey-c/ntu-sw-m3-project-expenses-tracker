package com.ntu.sw.expensestracker.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ntu.sw.expensestracker.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
