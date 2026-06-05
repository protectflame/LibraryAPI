package com.library.api.mapper;

import com.library.api.model.dto.ReaderDTO;
import com.library.api.model.entity.Reader;
import org.springframework.stereotype.Component;

@Component
public class ReaderMapper {

    // Преобразует сущность Reader в DTO
    // Возвращает null, если переданный reader равен null
    public ReaderDTO toDTO(Reader reader) {
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

    // Преобразует DTO в сущность Reader
    // Возвращает null, если переданный dto равен null
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
