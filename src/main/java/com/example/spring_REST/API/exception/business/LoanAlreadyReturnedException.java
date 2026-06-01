package com.example.spring_REST.API.exception.business;

import com.example.spring_REST.API.exception.DomainConstraintViolationException;

public class LoanAlreadyReturnedException extends DomainConstraintViolationException {
    public LoanAlreadyReturnedException(String message) {
        super(message);
    }
}