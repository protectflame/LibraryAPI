package com.example.spring_REST.API.service;

import com.example.spring_REST.API.mapper.BookMapper;
import com.example.spring_REST.API.model.dto.BookDTO;
import com.example.spring_REST.API.model.entity.Book;
import com.example.spring_REST.API.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;


    @Override
    public BookDTO createBook(BookDTO dto) {
        Book book = bookMapper.toEntity(dto);

        book.setAvailableCopies(dto.getTotalCopies());
        book.setCreatedAt(LocalDateTime.now());

        Book savedBook = bookRepository.save(book);

        return bookMapper.toDTO(savedBook);
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()->new RuntimeException("Книга с ID " + id + " не найдена "));
        return bookMapper.toDTO(book);

    }

    @Override
    public List<BookDTO> getAllBook() {
        List<BookDTO> bookDTOList = bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .toList();
        return bookDTOList;
    }

    @Override
    public BookDTO removeBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->new RuntimeException("Книга для удаление не найдена"));
        bookRepository.delete(book);
        return null;
    }

    @Override
    public BookDTO update(Long id) {
        return null;
    }
}
