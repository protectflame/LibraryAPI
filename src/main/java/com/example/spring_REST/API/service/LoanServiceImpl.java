package com.example.spring_REST.API.service;

import com.example.spring_REST.API.exception.BookNotAvailableException;
import com.example.spring_REST.API.exception.LoanAlreadyReturnedException;
import com.example.spring_REST.API.exception.LoanNotFoundException;
import com.example.spring_REST.API.exception.ReaderNotFoundException;
import com.example.spring_REST.API.mapper.LoanMapper;
import com.example.spring_REST.API.model.dto.LoanDTO;
import com.example.spring_REST.API.model.entity.Book;
import com.example.spring_REST.API.model.entity.Loan;
import com.example.spring_REST.API.model.entity.LoanStatus;
import com.example.spring_REST.API.model.entity.Reader;
import com.example.spring_REST.API.repository.BookRepository;
import com.example.spring_REST.API.repository.LoanRepository;
import com.example.spring_REST.API.repository.ReaderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final LoanMapper loanMapper;

    public LoanServiceImpl(LoanRepository loanRepository,
                           BookRepository bookRepository,
                           ReaderRepository readerRepository,
                           LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
        this.loanMapper = loanMapper;
    }

    @Override
    public List<LoanDTO> getAll() {
        return loanRepository.findAll()
                .stream()
                .map(loanMapper::toDto)
                .toList();
    }

    @Override
    public LoanDTO getById(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found"));
        return loanMapper.toDto(loan);
    }

    @Override
    public LoanDTO createLoan(LoanDTO loanDTO) {
        Book book = bookRepository.findById(loanDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Reader reader = readerRepository.findById(loanDTO.getReaderId())
                .orElseThrow(() -> new ReaderNotFoundException("Reader not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new BookNotAvailableException("Book is not available");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Loan loan = loanMapper.toEntity(loanDTO, book, reader);
        loan.setIssueDate(LocalDateTime.now());
        loan.setStatus(LoanStatus.ACTIVE);

        return loanMapper.toDto(loanRepository.save(loan));
    }

    @Override
    public LoanDTO updateLoan(Long id, LoanDTO loanDTO) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found"));

        Book book = bookRepository.findById(loanDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Reader reader = readerRepository.findById(loanDTO.getReaderId())
                .orElseThrow(() -> new ReaderNotFoundException("Reader not found"));

        loan.setBook(book);
        loan.setReader(reader);
        loan.setIssueDate(loanDTO.getIssueDate());
        loan.setDueDate(loanDTO.getDueDate());
        loan.setReturnDate(loanDTO.getReturnDate());
        loan.setStatus(loanDTO.getStatus());

        return loanMapper.toDto(loanRepository.save(loan));
    }

    @Override
    public LoanDTO returnLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found"));

        if (loan.getStatus() == LoanStatus.RETURNED || loan.getReturnDate() != null) {
            throw new LoanAlreadyReturnedException("Loan already returned");
        }

        loan.setReturnDate(LocalDateTime.now());
        loan.setStatus(LoanStatus.RETURNED);

        Book book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        return loanMapper.toDto(loanRepository.save(loan));
    }

    @Override
    public List<LoanDTO> getActiveLoans() {
        return List.of();
    }

    @Override
    public List<LoanDTO> getOverdueLoans() {
        return List.of();
    }

    @Override
    public List<LoanDTO> getReaderHistory(Long readerId) {
        return List.of();
    }

    @Override
    public void deleteLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found"));
        loanRepository.delete(loan);
    }
}