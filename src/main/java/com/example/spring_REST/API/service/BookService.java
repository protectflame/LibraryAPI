package com.example.spring_REST.API.service;

import com.example.spring_REST.API.model.dto.BookDTO;
import com.example.spring_REST.API.model.entity.Book;
import org.springframework.http.ResponseEntity;

public interface BookService {
    public Book createBook(Book book);
}