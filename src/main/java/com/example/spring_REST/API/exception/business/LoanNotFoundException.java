package com.example.spring_REST.API.exception.business;

import com.example.spring_REST.API.exception.DomainConstraintViolationException;

public class LoanNotFoundException extends DomainConstraintViolationException {
    public LoanNotFoundException(String message) {
        super(message);
    }
}