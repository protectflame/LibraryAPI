package com.library.api.service;

import com.library.api.exception.notFound.AuthorNotFoundException;
import com.library.api.mapper.AuthorMapper;
import com.library.api.mapper.BookMapper;
import com.library.api.model.dto.AuthorDTO;
import com.library.api.model.dto.BookDTO;
import com.library.api.model.entity.Author;
import com.library.api.model.entity.Book;
import com.library.api.repository.AuthorRepository;
import com.library.api.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    @Override
    @Transactional
    public AuthorDTO create(AuthorDTO dto) {
        Author author = authorMapper.toEntity(dto);
        Author savedAuthor = authorRepository.save(author);
        return authorMapper.toDTO(savedAuthor);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDTO getById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Автор с ID " + id + " не найден"));
        return authorMapper.toDTO(author);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuthorDTO> getAll(Pageable pageable) {
        return authorRepository.findAll(pageable)
                .map(authorMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuthorDTO> searchByName(String query, Pageable pageable) {
        Page<Author> authorPage = authorRepository
                .findByFirstNameContainingOrLastNameContaining(query, pageable);
        return authorPage.map(authorMapper::toDTO);
    }


    @Override
    @Transactional
    public AuthorDTO update(Long id, AuthorDTO dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Автор с ID " + id + " не найден"));
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setBirthDate(dto.getBirthDate());
        authorRepository.save(author);
        return authorMapper.toDTO(author);
    }

    @Override
    @Transactional
    public AuthorDTO remove(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Автор с ID " + id + " не найден"));
        if(!bookRepository.existsByAuthorsId(id)){
            throw new IllegalStateException("Нельзя удалить автора у которого есть книги");
        }
        authorRepository.deleteById(id);
        return authorMapper.toDTO(author);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookDTO> getBooksByAuthorId(Long id, Pageable pageable) {
        Page<Book> booksPage = bookRepository.findByAuthorsId(id, pageable);
        return booksPage.map(bookMapper::toDTO);
    }
}
