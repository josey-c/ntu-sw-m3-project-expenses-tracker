package com.ntu.sw.expensestracker;

import lombok.Getter;
import lombok.Setter;

import org.springframework.stereotype.Component;

import javax.persistence.Table;

@Component
// @Getter and @Setter annotations> generate the getters and setters for us
@Getter
@Setter
@Table(name = "user")
public class User {
    
}
