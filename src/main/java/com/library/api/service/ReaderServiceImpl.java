package com.library.api.service;

import com.library.api.exception.notFound.ReaderNotFoundException;
import com.library.api.mapper.ReaderMapper;
import com.library.api.model.dto.ReaderDTO;
import com.library.api.model.entity.Reader;
import com.library.api.repository.ReaderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReaderServiceImpl implements ReaderService {

    private final ReaderRepository readerRepository;
    private final ReaderMapper readerMapper;

    @Override
    public List<ReaderDTO> getAll() {
        return readerRepository.findAll()
                .stream()
                .map(readerMapper::toDTO)
                .toList();
    }

    @Override
    public ReaderDTO getById(Long id) {
        Reader reader = readerRepository.findById(id)
                .orElseThrow(() -> new ReaderNotFoundException("Reader not found"));
        return readerMapper.toDTO(reader);
    }

    @Override
    public ReaderDTO create(ReaderDTO readerDTO) {
        Reader reader = readerMapper.toEntity(readerDTO);
        Reader savedReader = readerRepository.save(reader);
        return readerMapper.toDTO(savedReader);
    }

    @Override
    public ReaderDTO update(Long id, ReaderDTO readerDTO) {
        Reader existingReader = readerRepository.findById(id)
                .orElseThrow(() -> new ReaderNotFoundException("Reader not found"));

        existingReader.setName(readerDTO.getName());
        existingReader.setEmail(readerDTO.getEmail());
        existingReader.setPhone(readerDTO.getPhone());
        existingReader.setRegisteredAt(readerDTO.getRegisteredAt());
        existingReader.setStatus(readerDTO.getStatus());

        Reader updatedReader = readerRepository.save(existingReader);
        return readerMapper.toDTO(updatedReader);
    }

    @Override
    public void remove(Long id) {
        if (!readerRepository.existsById(id)) {
            throw new ReaderNotFoundException("Reader not found");
        }
        readerRepository.deleteById(id);
    }
}