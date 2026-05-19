package com.example.spring_REST.API.exception;

public class AuthorNotFoundException  extends ResourceNotFoundException {
    public AuthorNotFoundException(String message) {
        super(message);
    }

}
