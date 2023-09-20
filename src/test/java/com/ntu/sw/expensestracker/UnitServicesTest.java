package com.ntu.sw.expensestracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ntu.sw.expensestracker.entity.Category;
import com.ntu.sw.expensestracker.entity.Expense;
import com.ntu.sw.expensestracker.entity.User;
import com.ntu.sw.expensestracker.entity.Wallet;
import com.ntu.sw.expensestracker.repo.CategoryRepository;
import com.ntu.sw.expensestracker.repo.ExpenseRepository;
import com.ntu.sw.expensestracker.repo.UserRepository;
import com.ntu.sw.expensestracker.repo.WalletRepository;
import com.ntu.sw.expensestracker.services.ExpenseServiceImpl;

@SpringBootTest
public class UnitServicesTest {
    
    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    ExpenseServiceImpl expenseService;

    // @Test
    // public void createExpenseTest() {
    //     User user = new User("John", "john@gmail.com");
    //     when((userRepository.save(user))).thenReturn(user);

    //     Category food = new Category("food", 1, user);
    //     categoryRepository.save(food);
    //     when((categoryRepository.save(food))).thenReturn(food);

    //     Wallet main = new Wallet("main", user);
    //     walletRepository.save(main);
    //     when((walletRepository.save(main))).thenReturn(main);

    //     Expense expense = new Expense("macs", 10.0);

    //     when((expenseRepository.save(expense))).thenReturn(expense);
        
    //     Expense savedExpense = expenseService.createExpense(user.getId(), 1L, 1, expense);

    //     assertEquals(expense, savedExpense, "The saved expense should be the same as the new expense");

    // }

}
