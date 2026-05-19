package com.example.spring_REST.API.exception.business;

import com.example.spring_REST.API.exception.DomainConstraintViolationException;

public class CannotDeleteAuthorWithBooksException extends DomainConstraintViolationException {
    public CannotDeleteAuthorWithBooksException(String message) {
        super(message);
    }
}
