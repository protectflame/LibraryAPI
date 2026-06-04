package com.library.api.exception.business;

import com.library.api.exception.DomainConstraintViolationException;

public class CannotDeleteAuthorWithBooksException extends DomainConstraintViolationException {
    public CannotDeleteAuthorWithBooksException(String message) {
        super(message);
    }
}
