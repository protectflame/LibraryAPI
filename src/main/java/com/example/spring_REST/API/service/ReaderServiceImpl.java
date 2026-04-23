package com.example.spring_REST.API.service;

import com.example.spring_REST.API.exception.ReaderNotFoundException;
import com.example.spring_REST.API.mapper.ReaderMapper;
import com.example.spring_REST.API.model.dto.ReaderDTO;
import com.example.spring_REST.API.model.entity.Reader;
import com.example.spring_REST.API.repository.ReaderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderServiceImpl implements ReaderService {

    private final ReaderRepository readerRepository;
    private final ReaderMapper readerMapper;

    public ReaderServiceImpl(ReaderRepository readerRepository, ReaderMapper readerMapper) {
        this.readerRepository = readerRepository;
        this.readerMapper = readerMapper;
    }

    @Override
    public List<ReaderDTO> getAll() {
        return readerRepository.findAll()
                .stream()
                .map(readerMapper::toDto)
                .toList();
    }

    @Override
    public ReaderDTO getById(Long id) {
        Reader reader = readerRepository.findById(id)
                .orElseThrow(() -> new ReaderNotFoundException("Reader not found"));
        return readerMapper.toDto(reader);
    }

    @Override
    public ReaderDTO createReader(ReaderDTO readerDTO) {
        Reader reader = readerMapper.toEntity(readerDTO);
        Reader savedReader = readerRepository.save(reader);
        return readerMapper.toDto(savedReader);
    }

    @Override
    public ReaderDTO updateReader(Long id, ReaderDTO readerDTO) {
        Reader existingReader = readerRepository.findById(id)
                .orElseThrow(() -> new ReaderNotFoundException("Reader not found"));

        existingReader.setName(readerDTO.getName());
        existingReader.setEmail(readerDTO.getEmail());
        existingReader.setPhone(readerDTO.getPhone());
        existingReader.setRegisteredAt(readerDTO.getRegisteredAt());
        existingReader.setStatus(readerDTO.getStatus());

        Reader updatedReader = readerRepository.save(existingReader);
        return readerMapper.toDto(updatedReader);
    }

    @Override
    public void deleteReader(Long id) {
        if (!readerRepository.existsById(id)) {
            throw new ReaderNotFoundException("Reader not found");
        }
        readerRepository.deleteById(id);
    }
}