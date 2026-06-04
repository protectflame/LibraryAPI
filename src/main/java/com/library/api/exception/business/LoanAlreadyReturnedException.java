package com.library.api.exception.business;

import com.library.api.exception.DomainConstraintViolationException;

public class LoanAlreadyReturnedException extends DomainConstraintViolationException {
    public LoanAlreadyReturnedException(String message) {
        super(message);
    }
}