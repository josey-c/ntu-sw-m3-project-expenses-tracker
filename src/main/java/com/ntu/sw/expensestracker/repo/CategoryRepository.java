package com.ntu.sw.expensestracker.repo;

import java.util.List;

import javax.swing.text.html.parser.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ntu.sw.expensestracker.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository <Category, Long> {
    List<Category> findByCategoryNameIgnoreCase(String categoryName);
}
