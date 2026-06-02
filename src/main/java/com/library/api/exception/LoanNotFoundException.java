package com.library.api.exception;

public class LoanNotFoundException extends ResourceNotFoundException {
    public LoanNotFoundException(String message) {
        super(message);
    }
}