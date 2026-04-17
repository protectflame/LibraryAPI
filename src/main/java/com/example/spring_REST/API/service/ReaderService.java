package com.example.spring_REST.API.service;

import com.example.spring_REST.API.model.dto.ReaderDTO;

import java.util.List;

public interface ReaderService {
    ReaderDTO createReader(ReaderDTO readerDto);
    ReaderDTO updateReader(Long id, ReaderDTO readerDto);
    ReaderDTO getReaderById(Long id);
    List<ReaderDTO> getAll();
    void deleteReader(Long id);
}
