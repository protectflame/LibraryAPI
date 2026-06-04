package com.library.api.service;

import com.library.api.mapper.AuthorMapper;
import com.library.api.model.dto.AuthorDTO;
import com.library.api.model.entity.Author;
import com.library.api.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;   // если используешь mapper

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    void getAll_shouldReturnPagedAuthors() {
        // === Given (Подготовка) ===
        Pageable pageable = PageRequest.of(0, 10, Sort.by("lastName").ascending());

        Author author1 = new Author(1L, "Иван", "Тургенев", LocalDate.of(1818, 11, 9), null);
        Author author2 = new Author(2L, "Лев", "Толстой", LocalDate.of(1828, 9, 9), null);

        Page<Author> authorPage = new PageImpl<>(List.of(author1, author2), pageable, 2);

        AuthorDTO dto1 = new AuthorDTO(1L, "Иван", "Тургенев", null);
        AuthorDTO dto2 = new AuthorDTO(2L, "Лев", "Толстой", null);

        when(authorRepository.findAll(pageable)).thenReturn(authorPage);
        when(authorMapper.toDTO(author1)).thenReturn(dto1);
        when(authorMapper.toDTO(author2)).thenReturn(dto2);

        // === When (Вызов) ===
        Page<AuthorDTO> result = authorService.getAll(pageable);

        // === Then (Проверка) ===
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent().get(0).getLastName()).isEqualTo("Тургенев");
    }
}