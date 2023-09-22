package com.ntu.sw.expensestracker.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {



    @Id
    @Column(name = "categoryId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int categoryNum;

    @Column(name = "category_name")
    @Size(min = 3, max = 30, message = "Category name must be between 3 and 30 charcters")
    String categoryName;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "userid")
    private User user;

    // @OneToMany(mappedBy = "category")
    // private List<Expense> expenses;
    public Category() {

    }

    public Category (String categoryName) {
        this.categoryName = categoryName;
    }

    public Category (String categoryName, int categoryNum, User user) {
        this.categoryName = categoryName;
        this.categoryNum = categoryNum;
        this.user = user;
    }

}
