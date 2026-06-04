package com.library.api.exception.notFound;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(String message) {
        super(message);
    }
}