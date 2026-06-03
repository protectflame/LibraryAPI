package com.library.api.exception.notFound;

import com.library.api.exception.ResourceNotFoundException;

public class AuthorNotFoundException extends ResourceNotFoundException {
    public AuthorNotFoundException(String message) {
        super(message);
    }

}
