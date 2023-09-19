package com.ntu.sw.expensestracker.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
    private String description;

    @Column(name = "amount")
    private Double amount;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "wallet_id", referencedColumnName = "walletId")
    private Wallet wallet;

    // @OneToOne(mappedBy = "expense")
    // private Category category;

}
