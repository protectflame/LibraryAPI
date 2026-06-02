package com.example.spring_REST.API.service;

import com.example.spring_REST.API.model.dto.AuthorDTO;
import com.example.spring_REST.API.model.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    public BookDTO createBook(BookDTO bookDto);

    public BookDTO getBookById(Long id);

    public Page<BookDTO> searchBooks(String title, String genre, String authorName, Boolean available, Pageable pageable);

    public List<AuthorDTO> getAuthorsByBookId(Long bookId);

    public Page<BookDTO> getAllBooks(Pageable pageable);

    public BookDTO removeBook(Long id);

    public BookDTO update(Long id, BookDTO dto);

    List<BookDTO> findBooksByGenre(String genre);
}