package com.example.spring_REST.API.service;

import com.example.spring_REST.API.model.dto.BookDTO;
import com.example.spring_REST.API.model.entity.Book;
import com.example.spring_REST.API.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService{

    private BookRepository bookRepository;


    @Override
    public Book createBook(Book book) {
        Book book1 = bookRepository.save(book);
        return book;
    }
}
