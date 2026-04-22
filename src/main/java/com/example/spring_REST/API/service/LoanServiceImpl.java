package com.example.spring_REST.API.service;

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
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        return loanMapper.toDto(loan);
    }

    @Override
    public LoanDTO createLoan(LoanDTO loanDTO) {
        Book book = bookRepository.findById(loanDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Reader reader = readerRepository.findById(loanDTO.getReaderId())
                .orElseThrow(() -> new RuntimeException("Reader not found"));

        Loan loan = loanMapper.toEntity(loanDTO, book, reader);
        return loanMapper.toDto(loanRepository.save(loan));
    }

    @Override
    public LoanDTO updateLoan(Long id, LoanDTO loanDTO) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        Book book = bookRepository.findById(loanDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Reader reader = readerRepository.findById(loanDTO.getReaderId())
                .orElseThrow(() -> new RuntimeException("Reader not found"));

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
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setReturnDate(LocalDateTime.now());
        loan.setStatus(LoanStatus.RETURNED);

        return loanMapper.toDto(loanRepository.save(loan));
    }

    @Override
    public void deleteLoan(Long id) {
        if (!loanRepository.existsById(id)) {
            throw new RuntimeException("Loan not found");
        }
        loanRepository.deleteById(id);
    }
}