package com.bank.customer.ecxeption;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
