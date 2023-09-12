package com.ntu.sw.expensestracker.entity;

import lombok.Getter;
import lombok.Setter;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Component
@Getter
@Setter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "email")
    @Email(message = "Email should be valid")
    private String email;

    public User() {
    }

}
