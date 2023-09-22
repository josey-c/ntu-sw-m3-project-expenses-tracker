package com.ntu.sw.expensestracker.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Component
@Getter
@Setter
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long id;

    @Column(name = "first_name")
    @Size(min = 3, max =30, message = "First Name must be between 3 and 30 characters.")
    private String firstName;

    @Column(name = "email")
    @Email(message = "Email should be valid")
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Category> categories;

    @OneToMany(mappedBy = "user")
    private List<Wallet> wallets;

    public User() {
    }

    public User(String firstName, String email) {
        this.firstName = firstName;
        this.email = email;
    }

}
