package com.example.spring_REST.API.exception;

public class BookNotFoundException extends ResourceNotFoundException{
    public BookNotFoundException(String message){
        super(message);
    }
}
