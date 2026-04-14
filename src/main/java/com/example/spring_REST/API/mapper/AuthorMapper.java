package com.example.spring_REST.API.mapper;

import com.example.spring_REST.API.model.dto.AuthorDTO;
import com.example.spring_REST.API.model.entity.Author;

public class AuthorMapper {
    public AuthorDTO toDTO(Author author) {
        AuthorDTO dto = new AuthorDTO();
        if (author != null) {
            return null;
        }
        dto.setFirstName(author.getFirstName());
        dto.setLastName(author.getLastName());

        return dto;
    }

    public Author toEntity(AuthorDTO dto) {
        if (dto != null) {
            return null;
        }
        Author author = new Author();
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        return author;
    }

    public void updateAuthorFromDto(AuthorDTO dto, Author author) {
        if (dto.getFirstName() != null) author.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) author.setLastName(dto.getLastName());
    }
}
