package com.library.api.controller;

import com.library.api.model.dto.AuthorDTO;
import com.library.api.model.dto.BookDTO;
import com.library.api.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@AllArgsConstructor
@Validated
@Tag(name = "Books", description = "Управление книгами")
public class BookController {

    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Получить все книги", description = "Пагинированный список всех книг")
    public Page<BookDTO> getAllBooks(@ParameterObject Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить книгу по ID")
    public ResponseEntity<BookDTO> getBookById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    @Operation(summary = "Создать книгу",
            description = "Добавляет новую книгу. Авторы должны уже существовать.")
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO dto) {
        BookDTO created = bookService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить книгу")
    public ResponseEntity<BookDTO> updateBook(
            @PathVariable @Min(1) Long id,
            @Valid @RequestBody BookDTO dto) {
        return ResponseEntity.ok(bookService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить книгу")
    public ResponseEntity<Void> deleteBook(@PathVariable @Min(1) Long id) {
        bookService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/authors")
    @Operation(summary = "Получить авторов книги")
    public List<AuthorDTO> getAuthorsByBookId(@PathVariable @Min(1) Long id) {
        return bookService.getAuthorsById(id);
    }
}