package com.example.spring_REST.API.exception.notFound;

import com.example.spring_REST.API.exception.ResourceNotFoundException;

public class ReaderNotFoundException extends ResourceNotFoundException {
    public ReaderNotFoundException(String message) {
        super(message);
    }
}