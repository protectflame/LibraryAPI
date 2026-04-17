package com.example.spring_REST.API.service;

import com.example.spring_REST.API.model.dto.ReaderDTO;
import com.example.spring_REST.API.repository.ReaderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderServiceImpl implements ReaderService {

    private final ReaderRepository readerRepository;

    public ReaderServiceImpl(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public ReaderDTO createReader(ReaderDTO readerDto) {
        return null;
    }

    @Override
    public ReaderDTO updateReader(Long id, ReaderDTO readerDto) {
        return null;
    }

    @Override
    public ReaderDTO getReaderById(Long id) {
        return null;
    }

    @Override
    public List<ReaderDTO> getAll() {
        return readerRepository.findAll()
                .stream()
                .map(reader -> new ReaderDTO(reader.getId(), reader.getName(), reader.getEmail()))
                .toList();
    }

    @Override
    public void deleteReader(Long id) {

    }
}