package com.bank.customer;

import com.bank.customer.client.CreateLoanApplication;
import com.bank.customer.controller.CustomerController;
import com.bank.customer.dto.CustomerDto;
import com.bank.customer.entity.Customer;
import com.bank.customer.service.CustomerService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CreateLoanApplication createLoanApplication;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setNationalIdentityNumber("123456789");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        // Set other fields as needed
    }

    @Test
    void testGetAllCustomers() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/customer/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nationalIdentityNumber").value("123456789"));

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    void testSubmitLoanApplication() throws Exception {
        mockMvc.perform(post("/customer/submit-loan-application")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nationalIdentityNumber\":\"123456789\"}")) // Example JSON data
                .andExpect(status().isOk())
                .andExpect(content().string("Save successfully...."));

        verify(customerService, times(1)).addCustomer(any(Customer.class));
    }

    @Test
    void testAddCustomer() throws Exception {
        mockMvc.perform(post("/customer/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nationalIdentityNumber\":\"123456789\"}")) // Example JSON data
                .andExpect(status().isCreated());

        verify(customerService, times(1)).addCustomer(any(Customer.class));
    }

    @Test
    void testFindAllWithEmployees() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/customer/with-employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nationalIdentityNumber").value("123456789"));

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    void testGetCustomerByNationalIdentityNumber() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setNationalIdentityNumber("123456789");

        when(customerService.getCustomerByNationalIdentityNumber("123456789")).thenReturn(customerDto);

        mockMvc.perform(get("/customer/get/123456789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nationalIdentityNumber").value("123456789"));

        verify(customerService, times(1)).getCustomerByNationalIdentityNumber("123456789");
    }
}
