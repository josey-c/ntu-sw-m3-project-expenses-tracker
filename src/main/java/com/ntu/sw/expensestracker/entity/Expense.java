package com.ntu.sw.expensestracker.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "expense")
@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate expenseDate;

    @Column(name = "description")
    @Size(min = 3, max = 30, message= "Description must be between 3 and 30 characters")
    private String description;

    @Column(name = "amount")
    @Positive(message = "Amount must be above 0")
    private Double amount;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "wallet_id", referencedColumnName = "walletId")
    private Wallet wallet;

    // @JsonBackReference
    @ManyToOne(optional = true)
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private Category category;

    public Expense() {

    }

    public Expense(String description, Double amount) {
        this.description = description;
        this.amount = amount;
    }

}
