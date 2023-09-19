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
    String categoryName;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "userid")
    private User user;

    // @OneToMany(mappedBy = "category")
    // private List<Expense> expenses;

}
