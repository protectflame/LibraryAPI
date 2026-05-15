package com.example.spring_REST.API.exception.notFound;

import com.example.spring_REST.API.exception.ResourceNotFoundException;

public class AuthorNotFoundException  extends ResourceNotFoundException {
    public AuthorNotFoundException(String message) {
        super(message);
    }

}
