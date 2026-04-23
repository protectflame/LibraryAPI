package com.example.spring_REST.API.mapper;

import com.example.spring_REST.API.model.dto.AuthorDTO;
import com.example.spring_REST.API.model.entity.Author;
import org.springframework.stereotype.Component; // Рекомендуется добавить компонент для внедрения

@Component
public class AuthorMapper {

    public AuthorDTO toDTO(Author author) {
        // ИСПРАВЛЕНИЕ: проверяем на null правильно
        if (author == null) {
            return null;
        }

        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setFirstName(author.getFirstName());
        dto.setLastName(author.getLastName());
        // birthDate тоже можно мапить, если нужно
        return dto;
    }

    public Author toEntity(AuthorDTO dto) {
        if (dto == null) {
            return null;
        }

        Author author = new Author();
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        // Если есть ID, устанавливаем его (для обновлений)
        if (dto.getId() != null) {
            author.setId(dto.getId());
        }
        return author;
    }

    public void updateAuthorFromDto(AuthorDTO dto, Author author) {
        if (dto == null || author == null) return;

        if (dto.getFirstName() != null) author.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) author.setLastName(dto.getLastName());
    }
}