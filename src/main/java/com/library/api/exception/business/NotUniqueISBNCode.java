package com.library.api.exception.business;

import com.library.api.exception.DomainConstraintViolationException;

public class NotUniqueISBNCode extends DomainConstraintViolationException {
    public NotUniqueISBNCode(String message) {
        super(message);
    }
}
