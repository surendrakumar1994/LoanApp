package com.bank.loneapplication.controller;

import com.bank.loneapplication.entity.Loan;
import com.bank.loneapplication.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoanController.class)
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    private Loan loan;

    @BeforeEach
    void setUp() {
        loan = new Loan();
        loan.setId(1L);
        // Initialize other Loan properties
    }

    @Test
    void testCreateLoan() throws Exception {
        mockMvc.perform(post("/api/v1/loan/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"amount\":1000}")) // Example JSON data
                .andExpect(status().isCreated());

        verify(loanService, times(1)).createLoan(any(Loan.class));
    }

    @Test
    void testGetLoanById() throws Exception {
        when(loanService.getLoanById(1L)).thenReturn(loan);

        mockMvc.perform(get("/api/v1/loan/get/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(loanService, times(1)).getLoanById(1L);
    }

    @Test
    void testUpdateLoan() throws Exception {
        mockMvc.perform(put("/api/v1/loan/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"amount\":2000}")) // Example JSON data
                .andExpect(status().isOk());

        verify(loanService, times(1)).updateLoan(eq(1L), any(Loan.class));
    }

    @Test
    void testUpdateLoanPartially() throws Exception {
        Map<Object, Object> objectMap = new HashMap<>();
        objectMap.put("amount", 1500);

        mockMvc.perform(patch("/api/v1/loan/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":1500}")) // Example JSON data
                .andExpect(status().isOk());

        verify(loanService, times(1)).updateLoanPartially(eq(1L), anyMap());
    }

    @Test
    void testDeleteLoan() throws Exception {
        mockMvc.perform(delete("/api/v1/loan/delete/1"))
                .andExpect(status().isOk());

        verify(loanService, times(1)).deleteLoan(1L);
    }
}
