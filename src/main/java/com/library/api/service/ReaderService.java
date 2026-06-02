package com.library.api.service;

import com.library.api.model.dto.ReaderDTO;

import java.util.List;

public interface ReaderService {
    ReaderDTO createReader(ReaderDTO readerDto);
    ReaderDTO updateReader(Long id, ReaderDTO readerDto);
    ReaderDTO getById(Long id);
    List<ReaderDTO> getAll();
    void deleteReader(Long id);
}
