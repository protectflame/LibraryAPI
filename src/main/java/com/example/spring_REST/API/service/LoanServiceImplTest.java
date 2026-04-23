package com.example.spring_REST.API.service;

import com.example.spring_REST.API.exception.LoanAlreadyReturnedException;
import com.example.spring_REST.API.exception.LoanNotFoundException;
import com.example.spring_REST.API.mapper.LoanMapper;
import com.example.spring_REST.API.model.dto.LoanDTO;
import com.example.spring_REST.API.model.entity.Book;
import com.example.spring_REST.API.model.entity.Loan;
import com.example.spring_REST.API.model.entity.LoanStatus;
import com.example.spring_REST.API.model.entity.Reader;
import com.example.spring_REST.API.repository.BookRepository;
import com.example.spring_REST.API.repository.LoanRepository;
import com.example.spring_REST.API.repository.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ReaderRepository readerRepository;

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private LoanServiceImpl loanService;

    private Book book;
    private Reader reader;
    private Loan loan;
    private LoanDTO loanDTO;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setAvailableCopies(2L);

        reader = new Reader();
        reader.setId(1L);

        loan = new Loan();
        loan.setId(1L);
        loan.setBook(book);
        loan.setReader(reader);
        loan.setIssueDate(LocalDateTime.now());
        loan.setDueDate(LocalDateTime.now().plusDays(14));
        loan.setStatus(LoanStatus.ACTIVE);

        loanDTO = new LoanDTO();
        loanDTO.setId(1L);
        loanDTO.setBookId(1L);
        loanDTO.setReaderId(1L);
        loanDTO.setIssueDate(LocalDateTime.now());
        loanDTO.setDueDate(LocalDateTime.now().plusDays(14));
        loanDTO.setReturnDate(null);
        loanDTO.setStatus(LoanStatus.ACTIVE);
    }

    @Test
    void createLoan_shouldCreateLoanSuccessfully() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));
        when(loanMapper.toEntity(loanDTO, book, reader)).thenReturn(loan);
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(loanMapper.toDto(any(Loan.class))).thenReturn(loanDTO);

        LoanDTO result = loanService.createLoan(loanDTO);

        assertEquals(1L, result.getId());
        verify(bookRepository).save(book);
        verify(loanRepository).save(loan);
        verify(loanMapper).toEntity(loanDTO, book, reader);
        verify(loanMapper).toDto(loan);
    }

    @Test
    void returnLoan_shouldReturnLoanSuccessfully() {
        loan.setReturnDate(null);
        loan.setStatus(LoanStatus.ACTIVE);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(loanMapper.toDto(any(Loan.class))).thenReturn(loanDTO);

        LoanDTO result = loanService.returnLoan(1L);

        assertEquals(1L, result.getId());
        verify(loanRepository).findById(1L);
        verify(bookRepository).save(book);
        verify(loanRepository).save(loan);
    }

    @Test
    void returnLoan_shouldThrowExceptionIfAlreadyReturned() {
        loan.setReturnDate(LocalDateTime.now());
        loan.setStatus(LoanStatus.RETURNED);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        assertThrows(LoanAlreadyReturnedException.class, () -> loanService.returnLoan(1L));

        verify(loanRepository).findById(1L);
        verifyNoMoreInteractions(bookRepository, loanRepository, readerRepository, loanMapper);
    }

    @Test
    void getById_shouldThrowExceptionIfNotFound() {
        when(loanRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(LoanNotFoundException.class, () -> loanService.getById(99L));

        verify(loanRepository).findById(99L);
    }
}