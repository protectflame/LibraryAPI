package com.example.spring_REST.API.service;

import com.example.spring_REST.API.exception.ReaderNotFoundException;
import com.example.spring_REST.API.mapper.ReaderMapper;
import com.example.spring_REST.API.model.dto.ReaderDTO;
import com.example.spring_REST.API.model.entity.LoanStatus;
import com.example.spring_REST.API.model.entity.Reader;
import com.example.spring_REST.API.repository.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReaderServiceImplTest {

    @Mock
    private ReaderRepository readerRepository;

    @Mock
    private ReaderMapper readerMapper;

    @InjectMocks
    private ReaderServiceImpl readerService;

    private Reader reader;
    private ReaderDTO readerDTO;

    @BeforeEach
    void setUp() {
        reader = new Reader();
        reader.setId(1L);
        reader.setName("Ivan Ivanov");
        reader.setEmail("ivan@mail.com");
        reader.setPhone("1234567890");
        reader.setRegisteredAt(LocalDate.now());
        reader.setStatus(LoanStatus.ACTIVE);

        readerDTO = new ReaderDTO();
        readerDTO.setId(1L);
        readerDTO.setName("Ivan Ivanov");
        readerDTO.setEmail("ivan@mail.com");
        readerDTO.setPhone("1234567890");
        readerDTO.setRegisteredAt(LocalDate.now());
        readerDTO.setStatus(LoanStatus.ACTIVE);
    }

    @Test
    void getAll_shouldReturnAllReaders() {
        when(readerRepository.findAll()).thenReturn(List.of(reader));
        when(readerMapper.toDto(reader)).thenReturn(readerDTO);

        List<ReaderDTO> result = readerService.getAll();

        assertEquals(1, result.size());
        assertEquals("Ivan Ivanov", result.getFirst().getName());
        verify(readerRepository).findAll();
        verify(readerMapper).toDto(reader);
    }

    @Test
    void getById_shouldReturnReaderIfExists() {
        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));
        when(readerMapper.toDto(reader)).thenReturn(readerDTO);

        ReaderDTO result = readerService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(readerRepository).findById(1L);
        verify(readerMapper).toDto(reader);
    }

    @Test
    void getById_shouldThrowExceptionIfNotFound() {
        when(readerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ReaderNotFoundException.class, () -> readerService.getById(99L));

        verify(readerRepository).findById(99L);
        verifyNoInteractions(readerMapper);
    }

    @Test
    void createReader_shouldSaveAndReturnDto() {
        Reader savedReader = new Reader();
        savedReader.setId(1L);
        savedReader.setName("Ivan Ivanov");
        savedReader.setEmail("ivan@mail.com");
        savedReader.setPhone("1234567890");
        savedReader.setRegisteredAt(LocalDate.now());
        savedReader.setStatus(LoanStatus.ACTIVE);

        when(readerMapper.toEntity(readerDTO)).thenReturn(reader);
        when(readerRepository.save(reader)).thenReturn(savedReader);
        when(readerMapper.toDto(savedReader)).thenReturn(readerDTO);

        ReaderDTO result = readerService.createReader(readerDTO);

        assertNotNull(result);
        assertEquals("Ivan Ivanov", result.getName());
        verify(readerMapper).toEntity(readerDTO);
        verify(readerRepository).save(reader);
        verify(readerMapper).toDto(savedReader);
    }

    @Test
    void updateReader_shouldUpdateExistingReader() {
        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));
        when(readerRepository.save(any(Reader.class))).thenReturn(reader);
        when(readerMapper.toDto(reader)).thenReturn(readerDTO);

        ReaderDTO result = readerService.updateReader(1L, readerDTO);

        assertNotNull(result);
        assertEquals("Ivan Ivanov", result.getName());
        verify(readerRepository).findById(1L);
        verify(readerRepository).save(reader);
        verify(readerMapper).toDto(reader);
    }

    @Test
    void deleteReader_shouldDeleteIfExists() {
        when(readerRepository.existsById(1L)).thenReturn(true);

        readerService.deleteReader(1L);

        verify(readerRepository).existsById(1L);
        verify(readerRepository).deleteById(1L);
    }

    @Test
    void deleteReader_shouldThrowExceptionIfNotFound() {
        when(readerRepository.existsById(99L)).thenReturn(false);

        assertThrows(ReaderNotFoundException.class, () -> readerService.deleteReader(99L));

        verify(readerRepository).existsById(99L);
        verify(readerRepository, never()).deleteById(anyLong());
    }
}