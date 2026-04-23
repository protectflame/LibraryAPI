package com.example.spring_REST.API.mapper;

import com.example.spring_REST.API.model.dto.AuthorDTO;
import com.example.spring_REST.API.model.dto.BookDTO;
import com.example.spring_REST.API.model.entity.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {
    public static BookDTO toDTO(Book book) {
        if (book == null) return null;

        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setDescription(book.getDescrition());
        dto.setPublishYear(book.getPublishYear());
        dto.setGenre(book.getGenre());
        dto.setTotalCopies(book.getTotalCopies());
        //dto.setAvailableCopies(book.isAvailableCopies());
        dto.setCreatedAt(book.getCreatedAt());

        if (book.getAuthors() != null) {
            List<AuthorDTO> authorDtos = book.getAuthors().stream()
                    .map(author -> new AuthorDTO(
                            author.getId(),
                            author.getFirstName(),
                            author.getLastName()
                    ))
                    .collect(Collectors.toList());
            dto.setAuthors(authorDtos);
        } else {
            dto.setAuthors(List.of()); // Пустой список вместо null
        }

        return dto;
    }

    public static Book toEntity(BookDTO dto) {
        if (dto != null) {
            return null;
        }
        Book book = new Book();
        book.setTitle(dto.getTitle());
//        book.setDescription(dto.getDescription());
        book.setGenre(dto.getGenre());
        book.setTotalCopies(dto.getTotalCopies());
        book.setPublishYear(dto.getPublishYear());
//        if(dto.getAuthors()!=null){
//            List<AuthorDTO> authorDTOS = dto.getAuthors().stream().map(author -> new AuthorDTO(
//                    author.getId(),
//                    author.getFirstName(),
//                    author.getLastName()
//            )).collect(Collectors.toList());
//            book.setAuthors(authorDTOS);
//        }
//        else{
//            book.setAuthors(List.of());
//        }
        return book;
    }
}
