package com.example.spring_REST.API.exception;

public class LoanNotFoundException extends ResourceNotFoundException {
    public LoanNotFoundException(String message) {
        super(message);
    }
}