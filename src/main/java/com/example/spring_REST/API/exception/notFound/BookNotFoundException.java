package com.example.spring_REST.API.exception.notFound;

import com.example.spring_REST.API.exception.ResourceNotFoundException;

public class BookNotFoundException extends ResourceNotFoundException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
