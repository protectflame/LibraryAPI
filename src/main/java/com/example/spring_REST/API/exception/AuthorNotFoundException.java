package com.example.spring_REST.API.exception;

public class AuthorNotFoundException  extends RuntimeException {
    public AuthorNotFoundException(String message) {
        super(message);
    }

}
