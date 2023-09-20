package com.ntu.sw.expensestracker.dataloader;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ntu.sw.expensestracker.entity.Category;
import com.ntu.sw.expensestracker.entity.User;
import com.ntu.sw.expensestracker.entity.Wallet;
import com.ntu.sw.expensestracker.repo.CategoryRepository;
import com.ntu.sw.expensestracker.repo.UserRepository;
import com.ntu.sw.expensestracker.repo.WalletRepository;

@Component
public class DataLoader {
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private WalletRepository walletRepository;

    @Autowired 
    public DataLoader(UserRepository userRepository,CategoryRepository categoryRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.walletRepository = walletRepository;
    }

    @PostConstruct
    public void loadData() {
        userRepository.deleteAll();
        categoryRepository.deleteAll();
        walletRepository.deleteAll();

        User user = new User("John", "john@gmail.com");
        userRepository.save(user);

        Category food = new Category("food", 1, user);
        Category transport = new Category("transport", 2, user);
        Category bills = new Category("bills", 3, user);
        categoryRepository.save(food);
        categoryRepository.save(transport);
        categoryRepository.save(bills);

        Wallet main = new Wallet("main", user);
        walletRepository.save(main);

    }
}
