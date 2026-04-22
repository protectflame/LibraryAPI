package com.example.spring_REST.API.mapper;

import com.example.spring_REST.API.model.dto.ReaderDTO;
import com.example.spring_REST.API.model.entity.Reader;
import org.springframework.stereotype.Component;

@Component
public class ReaderMapper {

    public ReaderDTO toDto(Reader reader) {
        if (reader == null) return null;

        return new ReaderDTO(
                reader.getId(),
                reader.getName(),
                reader.getEmail(),
                reader.getPhone(),
                reader.getRegisteredAt(),
                reader.getStatus()
        );
    }

    public Reader toEntity(ReaderDTO dto) {
        if (dto == null) return null;

        Reader reader = new Reader();
        reader.setId(dto.getId());
        reader.setName(dto.getName());
        reader.setEmail(dto.getEmail());
        reader.setPhone(dto.getPhone());
        reader.setRegisteredAt(dto.getRegisteredAt());
        reader.setStatus(dto.getStatus());
        return reader;
    }
}