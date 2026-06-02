package com.library.api.service;

import com.library.api.exception.BookNotFoundException;
import com.library.api.mapper.AuthorMapper;
import com.library.api.mapper.BookMapper;
import com.library.api.model.dto.AuthorDTO;
import com.library.api.model.dto.BookDTO;
import com.library.api.model.entity.Author;
import com.library.api.model.entity.Book;
import com.library.api.repository.AuthorRepository;
import com.library.api.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Сервис для управления книгами в библиотеке.
 *
 * <p>Содержит бизнес-логику по созданию, обновлению и поиску книг,
 * а также управлению связями с авторами.</p>
 */
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Transactional(readOnly = true)
    protected Set<Author> loadAndValidateAuthors(Set<Long> authorIds) {
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

    /**
     * Создаёт новую книгу в системе.
     *
     * @param dto данные для создания книги
     * @return созданная книга с присвоенным ID
     * @throws EntityNotFoundException если указанный автор не существует
     * @throws DuplicateIsbnException если ISBN уже существует
     */
    @Transactional
    @Override
    public BookDTO createBook(BookDTO dto) {
        Book book = bookMapper.toEntity(dto);
        book.setId(null);
        book.setAvailableCopies(dto.getTotalCopies());
        book.setCreatedAt(LocalDateTime.now());

        if (dto.getAuthors() != null) {
            Set<Long> authorsIds = dto.getAuthors()
                    .stream()
                    .map(AuthorDTO::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            book.setAuthors(new HashSet<>(loadAndValidateAuthors(authorsIds)));
        }

        Book savedBook = bookRepository.save(book);
        return bookMapper.toDTO(savedBook);
    }

    @Transactional(readOnly = true)
    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Книга с ID " + id + " не найдена"));
        return bookMapper.toDTO(book);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<BookDTO> getAllBooks(Pageable pageable) {
        Page<Book> booksPage = bookRepository.findAll(pageable);
        return booksPage.map(bookMapper::toDTO);
    }

    @Override
    public Page<BookDTO> searchBooks(String title, String genre, String authorName, Boolean available, Pageable pageable) {
        Page<Book> booksPage = bookRepository.findWithFilters(title, genre, authorName, available, pageable);
        return booksPage.map(bookMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDTO> getAuthorsByBookId(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Книга не найдена"));

        Set<Author> authors = book.getAuthors();

        return authors.stream()
                .map(authorMapper::toDTO)
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
    public BookDTO update(Long id, BookDTO dto) {
        Book book = bookRepository.findById(dto.getId())
                .orElseThrow(() -> new BookNotFoundException("Книга с ID " + dto.getId() + " не найдена"));

        // 2. Обновляем поля из DTO
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setDescription(dto.getDescription());
        book.setGenre(dto.getGenre());
        book.setPublishYear(dto.getPublishYear());
        book.setTotalCopies(dto.getTotalCopies());

        if (dto.getAuthors() != null) {
            Set<Long> authorIds = dto.getAuthors().stream()
                    .map(AuthorDTO::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            Set<Author> newAuthors = loadAndValidateAuthors(authorIds);
            book.setAuthors(newAuthors);
        }
        Book updatedBook = bookRepository.save(book);

        // 5. Возвращаем обновленный DTO
        return bookMapper.toDTO(updatedBook);
    }

    @Override
    public List<BookDTO> findAllBooksByGenre(String genre) {
        List<Book> books = bookRepository.findByGenre(genre);
        return books.stream().map(bookMapper::toDTO).toList();
    }
}
