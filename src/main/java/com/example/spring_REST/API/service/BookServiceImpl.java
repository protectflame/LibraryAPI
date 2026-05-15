package com.example.spring_REST.API.service;

import com.example.spring_REST.API.exception.notFound.BookNotFoundException;
import com.example.spring_REST.API.mapper.AuthorMapper;
import com.example.spring_REST.API.mapper.BookMapper;
import com.example.spring_REST.API.model.dto.AuthorDTO;
import com.example.spring_REST.API.model.dto.BookDTO;
import com.example.spring_REST.API.model.entity.Author;
import com.example.spring_REST.API.model.entity.Book;
import com.example.spring_REST.API.repository.AuthorRepository;
import com.example.spring_REST.API.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;

    @Transactional(readOnly = true)
    private Set<Author> loadAndValidateAuthors(Set<Long> authorIds) {
        if (authorIds == null || authorIds.isEmpty()) {
            return Collections.emptySet();
        }

        List<Author> foundAuthors = authorRepository.findAllById(authorIds);
        if (foundAuthors.size() != authorIds.size()) {
            Set<Long> foundIds = foundAuthors.stream().map(Author::getId).collect(Collectors.toSet());

            Set<Long> missingIds = authorIds.stream().filter(id -> !foundIds.contains(id)).collect(Collectors.toSet());

            throw new EntityNotFoundException("Не найдены авторы с ID: " + missingIds);
        }

        return new HashSet<>(foundAuthors);
    }

    @Override
    @Transactional
    public BookDTO createBook(BookDTO dto) {
        Book book = bookMapper.toEntity(dto);
        book.setId(null);
        book.setAvailableCopies(dto.getTotalCopies());
        book.setCreatedAt(LocalDateTime.now());
        if (dto.getAuthors() != null) {
            Set<Long> authorsIds = dto.getAuthors().stream().map(AuthorDTO::getId).filter(Objects::nonNull).collect(Collectors.toSet());
            book.setAuthors(new HashSet<>(loadAndValidateAuthors(authorsIds)));
        }
        bookRepository.save(book);
        return bookMapper.toDTO(book);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Книга с ID " + id + " не найдена"));
        return bookMapper.toDTO(book);
    }


    @Override
    public Page<BookDTO> getAllBooks(Pageable pageable) {
        Page<Long> idPage = bookRepository.findBookIds(pageable);

        if (idPage.isEmpty()) {
            return Page.empty(pageable);
        }

        List<Book> books = bookRepository.findAllByIdInWithAuthors(idPage.getContent());

        Map<Long, Book> byId = books.stream()
                .collect(Collectors.toMap(Book::getId, Function.identity()));

        List<BookDTO> dtoList = idPage.getContent().stream()
                .map(byId::get)
                .filter(Objects::nonNull)
                .map(bookMapper::toDTO)
                .toList();

        return new PageImpl<>(dtoList, pageable, idPage.getTotalElements());
    }

    @Override
    public Page<BookDTO> searchBooks(String title, String genre, String authorName, Boolean available, Pageable pageable) {
        Page<Book> booksPage = bookRepository.findWithFilters(title, genre, authorName, available, pageable);
        return booksPage.map(bookMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDTO> getAuthorsByBookId(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Книга с ID " + id + " не найдена"));
        Set<Author> authors = book.getAuthors();
        return authors.stream().map(authorMapper::toDTO).toList();
    }


    @Override
    @Transactional
    public BookDTO removeBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Книга с ID " + id + " не найдена"));
        BookDTO dto = bookMapper.toDTO(book);
        bookRepository.delete(book);
        return dto;
    }


    /**
     * Обновляет книгу по id
     *
     * @param id ID автора
     * @param dto новые данные для обновления автора
     * @return переданный dto
     */
    @Override
    public BookDTO update(Long id, BookDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Книга с ID " + id + " не найдена"));

        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setDescription(dto.getDescription());
        book.setGenre(dto.getGenre());
        book.setPublishYear(dto.getPublishYear());
        book.setTotalCopies(dto.getTotalCopies());

        if (dto.getAuthors() != null) {
            Set<Long> authorIds = dto.getAuthors().stream().map(AuthorDTO::getId).filter(Objects::nonNull).collect(Collectors.toSet());

            Set<Author> newAuthors = loadAndValidateAuthors(authorIds);
            book.setAuthors(newAuthors);
        }
        Book updatedBook = bookRepository.save(book);
        return bookMapper.toDTO(updatedBook);
    }

    /**
     * Возвращает список книг с соответствующим жанром
     *
     * @param genre жанр книги
     * @return список DTO книг
     */
    @Override
    public List<BookDTO> findBooksByGenre(String genre) {
        List<Book> books = bookRepository.findAllByGenre(genre);
        return books.stream().map(bookMapper::toDTO).toList();
    }
}
