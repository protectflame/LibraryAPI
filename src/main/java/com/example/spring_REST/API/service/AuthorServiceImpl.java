package com.example.spring_REST.API.service;

import com.example.spring_REST.API.exception.AuthorNotFoundException;
import com.example.spring_REST.API.mapper.AuthorMapper;
import com.example.spring_REST.API.model.dto.AuthorDTO;
import com.example.spring_REST.API.model.dto.BookDTO;
import com.example.spring_REST.API.model.entity.Author;
import com.example.spring_REST.API.repository.AuthorRepository;
import com.example.spring_REST.API.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final BookRepository bookRepository;

    @Override
    public AuthorDTO createAuthor(AuthorDTO dto) {
        Author author = authorMapper.toEntity(dto);

        Author savedAuthor = authorRepository.save(author);

        return authorMapper.toDTO(savedAuthor);
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Автор с ID " + id + " не найден"));
        return authorMapper.toDTO(author);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AuthorDTO> getAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable)
                .map(authorMapper::toDTO);
    }

    @Override
    public AuthorDTO updateAuthor(AuthorDTO dto) {
        Author author = authorRepository.findById(dto.getId())
                     .orElseThrow(() -> new AuthorNotFoundException("Автор с ID " + dto.getId() + " не найден"));
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setBirthDate(dto.getBirthDate());
        authorRepository.save(author);
        return dto;
    }

    @Override
    public void removeAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Автор с ID " + id + " не найден"));

        if (!author.getBooks().isEmpty()) {
            throw new IllegalStateException("Нельзя удалить автора, у которого есть книги");
        }
        authorRepository.deleteById(id);
    }

    @Override
    public List<BookDTO> getBooksByAuthorId(Long authorId) {
        return List.of();
    }
}
