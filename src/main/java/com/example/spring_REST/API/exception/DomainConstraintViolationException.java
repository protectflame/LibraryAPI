package com.example.spring_REST.API.exception;

public abstract class DomainConstraintViolationException extends RuntimeException {
    public DomainConstraintViolationException(String message) {
        super(message);
    }
}