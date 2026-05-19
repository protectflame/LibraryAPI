package com.example.spring_REST.API.exception;

public class ReaderNotFoundException extends ResourceNotFoundException {
    public ReaderNotFoundException(String message) {
        super(message);
    }
}