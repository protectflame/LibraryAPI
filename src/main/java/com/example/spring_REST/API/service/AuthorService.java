package com.example.spring_REST.API.service;

import com.example.spring_REST.API.model.dto.AuthorDTO;
import com.example.spring_REST.API.model.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {
    AuthorDTO createAuthor(AuthorDTO dto);
    AuthorDTO getAuthorById(Long id);
    Page<AuthorDTO> getAllAuthors(Pageable pageable); // Пагинация обязательна!
    AuthorDTO updateAuthor(AuthorDTO dto);
    void removeAuthor(Long id);
    List<BookDTO> getBooksByAuthorId(Long authorId);
}
