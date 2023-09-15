package com.ntu.sw.expensestracker.repo;

import java.util.List;

import javax.swing.text.html.parser.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ntu.sw.expensestracker.entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository <Wallet, Long> {
    List<Wallet> findByWalletNameIgnoreCase(String walletName);
}
