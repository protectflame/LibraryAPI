package com.example.spring_REST.API.service;

import com.example.spring_REST.API.exception.BookNotFoundException;
import com.example.spring_REST.API.mapper.BookMapper;
import com.example.spring_REST.API.model.dto.AuthorDTO;
import com.example.spring_REST.API.model.dto.BookDTO;
import com.example.spring_REST.API.model.entity.Author;
import com.example.spring_REST.API.model.entity.Book;
import com.example.spring_REST.API.repository.AuthorRepository;
import com.example.spring_REST.API.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;

    private Set<Author> loadAndValidateAuthors(Set<Long> authorIds) {
        if (authorIds == null || authorIds.isEmpty()) {
            return Collections.emptySet();
        }

        List<Author> foundAuthors = authorRepository.findAllById(authorIds);
        if (foundAuthors.size() != authorIds.size()) {
            Set<Long> foundIds = foundAuthors.stream()
                    .map(Author::getId)
                    .collect(Collectors.toSet());

            Set<Long> missingIds = authorIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toSet());

            throw new EntityNotFoundException("Не найдены авторы с ID: " + missingIds);
        }

        return new HashSet<>(foundAuthors);
    }

    @Transactional
    @Override
    public BookDTO createBook(BookDTO dto) {
        Book book = bookMapper.toEntity(dto);
        book.setAvailableCopies(dto.getTotalCopies());
        book.setCreatedAt(LocalDateTime.now());
        if (dto.getAuthors() != null) {
            Set<Long> authorsIds = dto.getAuthors()
                    .stream()
                    .map(AuthorDTO::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());;
            book.setAuthors(new HashSet<>(loadAndValidateAuthors(authorsIds)));
        }
        bookRepository.save(book);
        return bookMapper.toDTO(book);
    }




    @Transactional(readOnly = true)
    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Книга с ID " + id + " не найдена"));
        return bookMapper.toDTO(book);
    }




    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .toList();
    }






    @Transactional
    @Override
    public BookDTO removeBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Книга с ID " + id + " не найдена"));
        BookDTO dto = bookMapper.toDTO(book);
        bookRepository.delete(book);
        return dto;
    }






    @Override
    public BookDTO update(BookDTO dto) {
        Book book = bookRepository.findById
                (dto.getId()).orElseThrow(() -> new BookNotFoundException("Книга с ID " + dto.getId() + " не найдена "));
        book.setTitle(dto.getTitle());
        book.setTotalCopies(dto.getTotalCopies());
        book.setGenre(dto.getGenre());

return null;
    }
}
