package com.example.spring_REST.API.service;

import com.example.spring_REST.API.exception.notFound.AuthorNotFoundException;
import com.example.spring_REST.API.mapper.AuthorMapper;
import com.example.spring_REST.API.mapper.BookMapper;
import com.example.spring_REST.API.model.dto.AuthorDTO;
import com.example.spring_REST.API.model.dto.BookDTO;
import com.example.spring_REST.API.model.entity.Author;
import com.example.spring_REST.API.repository.AuthorRepository;
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
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

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

    @Override
    @Transactional(readOnly = true)
    public Page<AuthorDTO> getAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable)
                .map(authorMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuthorDTO> searchByName(String query, Pageable pageable) {
        Page<Author> page = authorRepository
                .findByFirstNameContainingOrLastNameContaining(query, pageable);

        return page.map(authorMapper::toDTO);
    }


    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Автор с ID " + id + " не найден"));
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setBirthDate(dto.getBirthDate());
        authorRepository.save(author);
        return authorMapper.toDTO(author);
    }

    @Override
    public void removeAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Автор с ID " + id + " не найден"));
        if (authorRepository.existsById(id)) {
            throw new IllegalStateException("Нельзя удалить автора, у которого есть книги");
        }
        authorRepository.deleteById(id);
    }

    @Override
    public List<BookDTO> getBooksByAuthorId(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Автор с ID " + authorId + " не найден"));

        return author.getBooks().stream().map(bookMapper::toDTO).toList();
    }
}
