package com.library.api.exception;

public class BookNotFoundException extends ResourceNotFoundException{
    public BookNotFoundException(String message){
        super(message);
    }
}
