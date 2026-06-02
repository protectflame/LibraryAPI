package com.library.api.exception;

public class AuthorNotFoundException  extends ResourceNotFoundException {
    public AuthorNotFoundException(String message) {
        super(message);
    }

}
