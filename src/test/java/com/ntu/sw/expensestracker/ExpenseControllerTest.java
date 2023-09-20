package com.ntu.sw.expensestracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntu.sw.expensestracker.entity.Category;
import com.ntu.sw.expensestracker.entity.Expense;
import com.ntu.sw.expensestracker.entity.Wallet;
import com.ntu.sw.expensestracker.exceptions.CategoryNotFound;
import com.ntu.sw.expensestracker.exceptions.WalletNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ExpenseControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createExpenseTest() throws Exception {
        
        // Wallet wallet = new Wallet("main");
        // String newWalletAsJSON = objectMapper.writeValueAsString(wallet);

        // // Request to create wallet for user
        // RequestBuilder request = MockMvcRequestBuilders.post("/users/1/wallets")
        //     .contentType(MediaType.APPLICATION_JSON)
        //     .content(newWalletAsJSON);

        // mockMvc.perform(request)
        //     .andExpect(status().isCreated())
        //     .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        //     .andExpect(jsonPath("$.walletId").value(1));
    
        // Category category = new Category("food");
        // String newCategoryAsJSON = objectMapper.writeValueAsString(category);

        // // Request to create category for user
        // RequestBuilder request2 = MockMvcRequestBuilders.post("/users/1/categories")
        //     .contentType(MediaType.APPLICATION_JSON)
        //     .content(newCategoryAsJSON);

        // mockMvc.perform(request2)
        //     .andExpect(status().isCreated())
        //     .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        //     .andExpect(jsonPath("$.categoryNum").value(1))
        //     .andExpect(jsonPath("$.categoryName").value("food"));

        Expense expense = new Expense("macs", 10.0);
        RequestBodyTempData data = new RequestBodyTempData(expense, 1);
        String newExpenseAsJSON= objectMapper.writeValueAsString(data);

        // Request to add expenses to wallet under user
        RequestBuilder request = MockMvcRequestBuilders.post("/users/1/wallets/1/expenses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newExpenseAsJSON);

        mockMvc.perform(request)
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.amount").value(10.0))
            .andExpect(jsonPath("$.description").value("macs"))
            .andExpect(jsonPath("$.category.categoryName").value("food"));
    }

    @Test
    public void getAllExpenseByWalletTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/users/1/wallets/1/expenses");

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    public void updateExpenseTest() throws Exception {
        Expense expense = new Expense("macs", 10.0);
        RequestBodyTempData data = new RequestBodyTempData(expense, 1);
        String newExpenseAsJSON= objectMapper.writeValueAsString(data);

        // Request to add expenses to wallet under user
        RequestBuilder request = MockMvcRequestBuilders.post("/users/1/wallets/1/expenses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newExpenseAsJSON);

        mockMvc.perform(request);

        Expense updatedExpense = new Expense("taxi", 15.0);
        RequestBodyTempData updatedData = new RequestBodyTempData(updatedExpense, 2);
        String updatedExpenseAsJSON= objectMapper.writeValueAsString(updatedData);

        RequestBuilder request2 = MockMvcRequestBuilders.put("/users/1/wallets/1/expenses/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedExpenseAsJSON);

        mockMvc.perform(request2)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.amount").value(15.0))
            .andExpect(jsonPath("$.description").value("taxi"))
            .andExpect(jsonPath("$.category.categoryName").value("transport"));
    }

    @Test 
    public void createExpenseWithWalletNotFoundExceptionTest() throws Exception {
        Expense expense = new Expense("macs", 10.0);
        RequestBodyTempData data = new RequestBodyTempData(expense, 1);
        String newExpenseAsJSON= objectMapper.writeValueAsString(data);

        // Request to add expenses to wallet under user
        RequestBuilder request = MockMvcRequestBuilders.post("/users/1/wallets/2/expenses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newExpenseAsJSON);

        mockMvc.perform(request)
            .andExpect(status().isNotFound())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof WalletNotFoundException))
            .andExpect(result -> assertEquals("Could not find wallet with id: 2", result.getResolvedException().getMessage()));
    }

    @Test
    public void updateExpenseWithCategoryNotFoundExceptionTest() throws Exception {
        Expense updatedExpense = new Expense("taxi", 15.0);
        RequestBodyTempData updatedData = new RequestBodyTempData(updatedExpense, 4);
        String updatedExpenseAsJSON= objectMapper.writeValueAsString(updatedData);

        RequestBuilder request2 = MockMvcRequestBuilders.put("/users/1/wallets/1/expenses/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedExpenseAsJSON);

        mockMvc.perform(request2)
            .andExpect(status().isBadRequest())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof CategoryNotFound))
            .andExpect(result -> assertEquals("Category with categoryNum: 4 does not exist", result.getResolvedException().getMessage()));
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

    public RequestBodyTempData(Expense expense, int categoryNum) {
        this.expense = expense;
        this.categoryNum = categoryNum;
    }
}