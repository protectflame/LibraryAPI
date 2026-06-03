package com.library.api.exception.notFound;

import com.library.api.exception.ResourceNotFoundException;

public class ReaderNotFoundException extends ResourceNotFoundException {
    public ReaderNotFoundException(String message) {
        super(message);
    }
}