package com.library.api.exception.notFound;

import com.library.api.exception.DomainConstraintViolationException;

public class LoanNotFoundException extends DomainConstraintViolationException {
    public LoanNotFoundException(String message) {
        super(message);
    }
}