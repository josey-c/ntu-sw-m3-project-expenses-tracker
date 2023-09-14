package com.ntu.sw.expensestracker.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.weaver.WeakClassLoaderReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ntu.sw.expensestracker.entity.Wallet;
import com.ntu.sw.expensestracker.exceptions.WalletNotFoundException;
import com.ntu.sw.expensestracker.repo.WalletRepository;

@Service
public class WalletServiceImpl implements WalletService{

    // constructor inject walletRepository
    private WalletRepository walletRepository;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    // CREATE 1 wallet
    @Override
    public Wallet createWallet(Wallet wallet) {
        Wallet newWallet = walletRepository.save(wallet);
        return newWallet; 
    }

    // GET 1 wallet
    @Override
    public Wallet getWallet(Long id) {
        Optional<Wallet> optionalWallet = walletRepository.findById(id);
        if (optionalWallet.isPresent()) {
            Wallet foundWallet = optionalWallet.get();
            return foundWallet;
        } else {
            throw new WalletNotFoundException(id);
        }

    }

    // GET all wallet
    @Override 
    public ArrayList<Wallet> getAllWallets(){
        List<Wallet> allWallets = walletRepository.findAll(); 
        return(ArrayList<Wallet>)allWallets; 
    }

    // UPDATE wallet
    @Override 
    public Wallet updateWallet(Long id, Wallet wallet){
        Optional<Wallet> optionWallet = walletRepository.findById(id); 
        
        if(optionWallet.isPresent()){
            Wallet walletToUpdate = optionWallet.get();
            walletToUpdate.setWalletName(wallet.getWalletName());
            return walletRepository.save(walletToUpdate); 
        }
        throw new WalletNotFoundException(id); 
    }

    // DELETE wallet
    @Override
    public void deleteById(Long id){
        walletRepository.deleteById(id);
    }

}
