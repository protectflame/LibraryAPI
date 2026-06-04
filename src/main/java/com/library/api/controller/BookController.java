package com.library.api.controller;

import com.library.api.model.dto.BookDTO;
import com.library.api.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@AllArgsConstructor
@Validated
@Tag(name = "Books", description = "Управление книгами: создание, поиск, обновление")
public class BookController {

    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Получение всех книг")
    public Page<BookDTO> getAllBooks(Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение книги по id")
    public BookDTO getBookById(@PathVariable @Min(1) Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    @Operation(summary = "Создать новую книгу", description = "Добавляет книгу в библиотеку. Авторы должны уже существовать в базе.")
    public ResponseEntity<BookDTO> createdBook(@Valid @RequestBody BookDTO dto) {
        BookDTO createdBook = bookService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление книги", description = "ID книги должен существовать иначе она не будет обновлена")
    public ResponseEntity<BookDTO> updateBook(@PathVariable @Min(1) Long id, @RequestBody BookDTO dto) {
        return ResponseEntity.ok(bookService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление по ID")
    public ResponseEntity<BookDTO> removeBook(@PathVariable @Min(1) Long id) {
        bookService.remove(id);
        return ResponseEntity.noContent().build();
    }
}