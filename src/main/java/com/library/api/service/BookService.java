package com.library.api.service;

import com.library.api.model.dto.AuthorDTO;
import com.library.api.model.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookDTO create(BookDTO bookDto);

    BookDTO getBookById(Long id);

    Page<BookDTO> searchBooks(String title, String genre, String authorName, Boolean available, Pageable pageable);

    List<AuthorDTO> getAuthorsById(Long bookId);

    Page<BookDTO> getAll(Pageable pageable);

    BookDTO remove(Long id);

    BookDTO update(Long id, BookDTO dto);

    List<BookDTO> findBooksByGenre(String genre);
}