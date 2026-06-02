package com.library.api.exception;

public class ReaderNotFoundException extends ResourceNotFoundException {
    public ReaderNotFoundException(String message) {
        super(message);
    }
}