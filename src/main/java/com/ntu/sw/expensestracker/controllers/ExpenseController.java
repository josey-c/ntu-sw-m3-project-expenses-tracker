package com.ntu.sw.expensestracker.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ntu.sw.expensestracker.entity.Expense;
import com.ntu.sw.expensestracker.services.ExpenseService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
// @RequestMapping("/expenses")
public class ExpenseController {

    private ExpenseService expenseService;

    // CREATE
    @PostMapping("users/{userId}/wallets/{walletId}/expenses")
    public ResponseEntity<Expense> createExpense(@PathVariable Long userId, @PathVariable Long walletId,
            @RequestBody RequestBodyTempData data) {
        System.out.println("JSON recieved! 🟢" + data.getExpense());
        Expense newExpense = expenseService.createExpense(userId, walletId, data.getCategoryNum(), data.getExpense());
        return new ResponseEntity<>(newExpense, HttpStatus.CREATED);
    }

    // READ ALL (GET ALL)
    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getAllExpense() {
        List<Expense> allExpense = expenseService.getAllExpense();
        return new ResponseEntity<>(allExpense, HttpStatus.OK);
    }

    // READ ALL (GET ALL) - by per wallet
    @GetMapping("users/{userId}/wallets/{walletId}/expenses")
    public ResponseEntity<List<Expense>> getAllExpenseByWallet(@PathVariable Long userId, @PathVariable Long walletId) {
        List<Expense> allExpense = expenseService.getAllExpenseByWallet(userId, walletId);
        return new ResponseEntity<>(allExpense, HttpStatus.OK);
    }

    // READ ONE (GET ONE)
    @GetMapping("/expenses/{id}")
    public ResponseEntity<Expense> getExpense(@PathVariable Long id) {
        Expense foundExpense = expenseService.getExpense(id);
        return new ResponseEntity<>(foundExpense, HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("users/{userId}/wallets/{walletId}/expenses/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long userId, @PathVariable Long walletId, @PathVariable Long id, @RequestBody Expense expense) {
        Expense updatedExpense = expenseService.updateExpense(userId, walletId, id, expense);
        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<HttpStatus> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

class RequestBodyTempData {
    Expense expense;
    int categoryNum;

    public Expense getExpense() {
        return expense;
    }
    public int getCategoryNum() {
        return categoryNum;
    }
}
