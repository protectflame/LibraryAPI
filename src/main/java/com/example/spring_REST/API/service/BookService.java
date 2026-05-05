package com.example.spring_REST.API.service;

import com.example.spring_REST.API.model.dto.BookDTO;

import java.util.List;

public interface BookService {
    public BookDTO createBook(BookDTO bookDto);
    public BookDTO getBookById(Long id);
    public List<BookDTO> getAllBooks();
    public BookDTO removeBook(Long id);
    public BookDTO update(BookDTO dto);
}