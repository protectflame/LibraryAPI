package com.library.api.service;

import com.library.api.exception.AuthorNotFoundException;
import com.library.api.mapper.AuthorMapper;
import com.library.api.mapper.BookMapper;
import com.library.api.model.dto.AuthorDTO;
import com.library.api.model.dto.BookDTO;
import com.library.api.model.entity.Author;
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
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final BookRepository bookRepository;
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
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Автор с ID " + authorId + " не найден"));

        return author.getBooks().stream().map(bookMapper::toDTO).toList();
    }
}
