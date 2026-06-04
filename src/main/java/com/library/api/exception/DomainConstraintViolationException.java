package com.library.api.exception;

public abstract class DomainConstraintViolationException extends RuntimeException {
    public DomainConstraintViolationException(String message) {
        super(message);
    }
}