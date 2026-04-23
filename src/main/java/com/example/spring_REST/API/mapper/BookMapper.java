package com.example.spring_REST.API.mapper;

import com.example.spring_REST.API.model.dto.BookDTO;
import com.example.spring_REST.API.model.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDTO toDTO(Book book) {
        if (book == null) return null;

        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setDescription(book.getDescription());
        dto.setPublishYear(book.getPublishYear());
        dto.setGenre(book.getGenre());
        dto.setTotalCopies(book.getTotalCopies());
        dto.setAvailableCopies(book.getAvailableCopies());
        dto.setCreatedAt(book.getCreatedAt());

        return dto;
    }

    public Book toEntity(BookDTO dto) {
        if (dto == null) return null;

        Book book = new Book();

        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setDescription(dto.getDescription());
        book.setPublishYear(dto.getPublishYear());
        book.setGenre(dto.getGenre());
        book.setTotalCopies(dto.getTotalCopies());

        // - availableCopies (зависит от totalCopies и выдач)
        // - createdAt (устанавливается при создании)
        // - authors (привязываются отдельно)

        return book;
    }
}