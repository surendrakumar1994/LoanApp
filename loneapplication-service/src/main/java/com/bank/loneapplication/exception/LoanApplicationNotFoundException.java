package com.bank.loneapplication.exception;

public class LoanApplicationNotFoundException extends RuntimeException {
    public LoanApplicationNotFoundException(String message) {
        super(message);
    }
}