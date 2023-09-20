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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "wallet")
@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "walletId")
    private long walletId;

    @Column(name = "wallet_name")
    private String walletName;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    private User user;

    @OneToMany(mappedBy = "wallet")
    private List<Expense> expenses;

    public Wallet() {

    }

    public Wallet(String walletName) {
        this.walletName = walletName;
    }

    public Wallet(String walletName, User user) {
        this.walletName = walletName;
        this.user = user;
    }

}
