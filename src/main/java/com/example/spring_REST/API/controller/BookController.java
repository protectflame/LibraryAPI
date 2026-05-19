package com.example.spring_REST.API.controller;

import com.example.spring_REST.API.model.dto.BookDTO;
import com.example.spring_REST.API.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//Версионирование нужно чтоб при изменении api все пользователи api могли спокойно пользоваться прошлой версией
// и плавно переходить на новый api
@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "Управление книгами: создание, поиск, обновление") // Группировка в Swagger
public class BookController {

    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Получение всех книг")
    public Page<BookDTO> getAll(Pageable pageable){
        return bookService.getAllBooks(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение книги по id")
    public BookDTO getById(@PathVariable Long id){
        return bookService.getBookById(id);
    }

    //Post - используется для создания чего-либо
    @PostMapping
    @Operation(
            summary = "Создать новую книгу",
            description = "Добавляет книгу в библиотеку. Авторы должны уже существовать в базе."
    )
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO dto) {
        BookDTO createdBook = bookService.createBook(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }
    @PutMapping
    @Operation(
            summary = "Обновление книги",
            description = "ID книги должен существовать иначе она не будет обновлена"
    )
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id){
        return ResponseEntity.ok(bookService.update(bookService.getBookById(id)));
    }
    @DeleteMapping
    @Operation(
            summary = "Удаление по ID"
    )
    public BookDTO removeBook(Long id){
        return bookService.removeBook(id);
    }
}