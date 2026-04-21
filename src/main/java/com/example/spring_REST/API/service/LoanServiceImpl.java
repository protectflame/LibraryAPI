package com.example.spring_REST.API.service;

import com.example.spring_REST.API.model.dto.LoanDTO;
import com.example.spring_REST.API.model.entity.Book;
import com.example.spring_REST.API.model.entity.Loan;
import com.example.spring_REST.API.repository.BookRepository;
import com.example.spring_REST.API.repository.LoanRepository;
import com.example.spring_REST.API.repository.ReaderRepository;
import com.example.spring_REST.API.service.LoanService;
import org.springframework.stereotype.Service;
import com.example.spring_REST.API.model.entity.Reader;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    public LoanServiceImpl(LoanRepository loanRepository,
                           BookRepository bookRepository,
                           ReaderRepository readerRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    @Override
    public List<LoanDTO> getAll() {
        return loanRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public LoanDTO getById(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        return toDto(loan);
    }

    @Override
    public LoanDTO createLoan(LoanDTO loanDTO) {
        Book book = bookRepository.findById(loanDTO.getId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Reader reader = readerRepository.findById(loanDTO.getReaderId())
                .orElseThrow(() -> new RuntimeException("Reader not found"));

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setReader(reader);
        loan.setIssueDate(loanDTO.getIssueDate());
        loan.setDueDate(loanDTO.getDueDate());
        loan.setReturnDate(loanDTO.getReturnDate());

        return toDto(loanRepository.save(loan));
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

        return toDto(loanRepository.save(loan));
    }

    @Override
    public LoanDTO returnLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setReturnDate(LocalDateTime.now());
        return toDto(loanRepository.save(loan));
    }

    private LoanDTO toDto(Loan loan) {
        return new LoanDTO(
                loan.getId(),
                loan.getBook().getId(),
                loan.getReader().getId(),
                loan.getIssueDate(),
                loan.getDueDate(),
                loan.getReturnDate()
        );
    }

    @Override
    public void deleteLoan(Long id) {
        // Проверяем, существует ли запись, и удаляем
        if (!loanRepository.existsById(id)) {
            throw new RuntimeException("Loan not found");
        }
        loanRepository.deleteById(id);
    }
}