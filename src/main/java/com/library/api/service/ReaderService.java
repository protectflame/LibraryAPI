package com.library.api.service;

import com.library.api.model.dto.ReaderDTO;

import java.util.List;

public interface ReaderService {
    ReaderDTO create(ReaderDTO readerDto);

    ReaderDTO update(Long id, ReaderDTO readerDto);

    ReaderDTO getById(Long id);

    List<ReaderDTO> getAll();

    void remove(Long id);
}
