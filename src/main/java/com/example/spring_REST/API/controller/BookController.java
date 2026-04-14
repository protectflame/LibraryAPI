package com.example.spring_REST.API.controller;

import com.example.spring_REST.API.service.BookServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/book")
public class BookController {
    private BookServiceImpl bookService;

    @GetMapping
    public String createBook(
//            @RequestBody Book book
    ){
//        Book savedBook = bookService.createBook(book);

        return "new ResponseEntity<>(savedBook, HttpStatus.CREATED)";
    }

}
