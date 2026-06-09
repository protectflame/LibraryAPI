package com.library.api.exception.business;

import com.library.api.exception.DomainConstraintViolationException;

public class NotRemoveBookWhereLoanActive extends DomainConstraintViolationException {
    public NotRemoveBookWhereLoanActive(String message) {
        super(message);
    }
}
