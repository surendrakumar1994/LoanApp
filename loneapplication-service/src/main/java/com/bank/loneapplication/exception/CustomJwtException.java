package com.bank.loneapplication.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class CustomJwtException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;
}