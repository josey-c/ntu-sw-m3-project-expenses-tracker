package com.ntu.sw.expensestracker.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ntu.sw.expensestracker.entity.Wallet;
import com.ntu.sw.expensestracker.exceptions.WalletNotFoundException;
import com.ntu.sw.expensestracker.services.WalletService;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/wallets")
public class WalletController {
    
    // call wallet service
    private WalletService walletService; 

    // GET 1 wallet
    @GetMapping("/{id}")
    public ResponseEntity<Wallet> getWallet(@PathVariable Long id){
        Wallet foundWallet = walletService.getWallet(id); 
        return new ResponseEntity<>(foundWallet, HttpStatus.OK);
    }

    // GET all wallet
    @GetMapping("")
    public ResponseEntity<ArrayList<Wallet>> getAllWallets(){
        ArrayList<Wallet> allWallets = walletService.getAllWallets();
        return new ResponseEntity<>(allWallets, HttpStatus.OK); 
    }

    // CREATE 1 wallet
    @PostMapping("")
    public ResponseEntity<Wallet> createWallet(@RequestBody Wallet wallet){
        return new ResponseEntity<>(walletService.createWallet(wallet), HttpStatus.CREATED); 
    }

    // UPDATE wallet 
    @PutMapping("/{id}")
    public ResponseEntity<Wallet> updateWallet(@PathVariable Long id, @RequestBody Wallet wallet){
        try{
            Wallet updatedWallet = walletService.updateWallet(id, wallet); 
            return new ResponseEntity<>(updatedWallet, HttpStatus.OK);
        }catch(WalletNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
    }

    // DELETE wallet
    @DeleteMapping("/{id}")
    public ResponseEntity<Wallet> deleteWallet(@PathVariable Long id){
        try{
            walletService.deleteById(id); 
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 

        }catch(WalletNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
    }



}
