package com.library.api.service;

import com.library.api.exception.notFound.BookNotAvailableException;
import com.library.api.exception.business.LoanAlreadyReturnedException;
import com.library.api.exception.business.LoanNotFoundException;
import com.library.api.exception.notFound.ReaderNotFoundException;
import com.library.api.mapper.LoanMapper;
import com.library.api.model.dto.LoanDTO;
import com.library.api.model.entity.Book;
import com.library.api.model.entity.Loan;
import com.library.api.model.entity.LoanStatus;
import com.library.api.model.entity.Reader;
import com.library.api.repository.BookRepository;
import com.library.api.repository.LoanRepository;
import com.library.api.repository.ReaderRepository;
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
                .map(loanMapper::toDTO)
                .toList();
    }

    @Override
    public LoanDTO getById(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found"));
        return loanMapper.toDTO(loan);
    }

    @Override
    public LoanDTO create(LoanDTO loanDTO) {
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

        return loanMapper.toDTO(loanRepository.save(loan));
    }

    @Override
    public LoanDTO update(Long id, LoanDTO loanDTO) {
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

        return loanMapper.toDTO(loanRepository.save(loan));
    }

    @Override
    public void remove(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found"));
        loanRepository.delete(loan);
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

        return loanMapper.toDTO(loanRepository.save(loan));
    }

    @Override
    public List<LoanDTO> getActiveLoans() {
        return loanRepository.findAll().stream()
                .filter(loan -> loan.getStatus() == LoanStatus.ACTIVE)
                .map(loanMapper::toDTO)
                .toList();
    }

    @Override
    public List<LoanDTO> getOverdueLoans() {
        return loanRepository.findAll().stream()
                .filter(loan -> loan.getStatus() == LoanStatus.ACTIVE)
                .filter(loan -> loan.getDueDate() != null && loan.getDueDate().isBefore(LocalDateTime.now()))
                .map(loanMapper::toDTO)
                .toList();
    }

    @Override
    public List<LoanDTO> getReaderHistory(Long readerId) {
        return loanRepository.findAll().stream()
                .filter(loan -> loan.getReader() != null && loan.getReader().getId().equals(readerId))
                .map(loanMapper::toDTO)
                .toList();
    }
}