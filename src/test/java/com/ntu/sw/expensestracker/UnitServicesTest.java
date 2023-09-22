package com.ntu.sw.expensestracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ntu.sw.expensestracker.entity.Category;
import com.ntu.sw.expensestracker.entity.Expense;
import com.ntu.sw.expensestracker.entity.User;
import com.ntu.sw.expensestracker.entity.Wallet;
import com.ntu.sw.expensestracker.exceptions.UserNotFoundException;
import com.ntu.sw.expensestracker.repo.CategoryRepository;
import com.ntu.sw.expensestracker.repo.ExpenseRepository;
import com.ntu.sw.expensestracker.repo.UserRepository;
import com.ntu.sw.expensestracker.repo.WalletRepository;
import com.ntu.sw.expensestracker.services.ExpenseServiceImpl;
import com.ntu.sw.expensestracker.services.UserServiceImpl;
import com.ntu.sw.expensestracker.services.WalletService;
import com.ntu.sw.expensestracker.services.WalletServiceImpl;

@SpringBootTest
public class UnitServicesTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    ExpenseServiceImpl expenseService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void createUserTest() {
        // 1. setup
        // create new user
        User user = new User("John", "john@gmail.com");

        // mock saved
        when((userRepository.save(user))).thenReturn(user);

        // 2. execute
        // call method that we want to test
        User savedUser = userService.createUser(user);

        // 3. assert
        // compare actual result with expected result
        assertEquals(user, savedUser, "the saved user should be same as user");

        // verify save method of customer repo is called once
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void getUserTest() {
        // 1. setup
        User user = new User("john", "john@gmail.com");

        Long userId = 2L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // 2. execute
        User retrievedUser = userService.getUser(userId);

        // 3. assert
        assertEquals(user, retrievedUser);
    }

    @Test
    public void GetUserNotFoundTest() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUser(userId));
    }

    // @Test
    // public void createExpenseTest() {
    // User user = new User("John", "john@gmail.com");
    // when((userRepository.save(user))).thenReturn(user);

    // Category food = new Category("food", 1, user);
    // categoryRepository.save(food);
    // when((categoryRepository.save(food))).thenReturn(food);

    // Wallet main = new Wallet("main", user);
    // walletRepository.save(main);
    // when((walletRepository.save(main))).thenReturn(main);

    // Expense expense = new Expense("macs", 10.0);

    // when((expenseRepository.save(expense))).thenReturn(expense);

    // Expense savedExpense = expenseService.createExpense(user.getId(), 1L, 1,
    // expense);

    // assertEquals(expense, savedExpense, "The saved expense should be the same as
    // the new expense");

    // }

}
