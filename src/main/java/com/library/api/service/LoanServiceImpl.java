package com.library.api.service;

import com.library.api.exception.business.BookNotAvailableException;
import com.library.api.exception.business.LoanAlreadyReturnedException;
import com.library.api.exception.notFound.LoanNotFoundException;
import com.library.api.exception.notFound.BookNotFoundException;
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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

// Реализация сервиса для управления выдачами книг
@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final LoanMapper loanMapper;

    @Override
    @Transactional(readOnly = true)
    public List<LoanDTO> getAll() {
        return loanRepository.findAll()
                .stream()
                .map(loanMapper::toDTO)
                .toList();
    }

    // Возвращает выдачу по идентификатору, выбрасывает исключение если не найдена
    @Override
    @Transactional(readOnly = true)
    public LoanDTO getById(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found"));
        return loanMapper.toDTO(loan);
    }

    // Создаёт новую выдачу: проверяет наличие книги, уменьшает количество доступных экземпляров
    @Override
    @Transactional
    public LoanDTO create(LoanDTO loanDTO) {
        Book book = bookRepository.findById(loanDTO.getBookId())
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        Reader reader = readerRepository.findById(loanDTO.getReaderId())
                .orElseThrow(() -> new ReaderNotFoundException("Reader not found"));

        // Проверяем доступность книги
        if (book.getAvailableCopies() <= 0) {
            throw new BookNotAvailableException("Book is not available");
        }

        // Уменьшаем количество доступных экземпляров
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Loan loan = loanMapper.toEntity(loanDTO, book, reader);
        loan.setIssueDate(LocalDateTime.now());
        loan.setStatus(LoanStatus.ACTIVE);

        return loanMapper.toDTO(loanRepository.save(loan));
    }

    // Обновляет данные выдачи по идентификатору
    @Override
    @Transactional
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

    // Удаляет выдачу по идентификатору
    @Override
    @Transactional
    public void remove(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found"));
        loanRepository.delete(loan);
    }

    // Оформляет возврат книги: устанавливает дату возврата и увеличивает количество доступных экземпляров
    @Override
    @Transactional
    public LoanDTO returnLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found"));

        // Проверяем, не была ли книга уже возвращена
        if (loan.getStatus() == LoanStatus.RETURNED || loan.getReturnDate() != null) {
            throw new LoanAlreadyReturnedException("Loan already returned");
        }

        loan.setReturnDate(LocalDateTime.now());
        loan.setStatus(LoanStatus.RETURNED);

        // Увеличиваем количество доступных экземпляров книги
        Book book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        return loanMapper.toDTO(loanRepository.save(loan));
    }

    // Возвращает список всех активных выдач
    @Override
    @Transactional(readOnly = true)
    public List<LoanDTO> getActiveLoans() {
        return loanRepository.findAll().stream()
                .filter(loan -> loan.getDueDate() != null && loan.getDueDate().isAfter(LocalDateTime.now()))
                .filter(loan -> loan.getStatus() == LoanStatus.ACTIVE)
                .map(loanMapper::toDTO)
                .toList();
    }

    // Возвращает список активных выдач с истёкшим сроком возврата
    @Override
    @Transactional(readOnly = true)
    public List<LoanDTO> getOverdueLoans() {
        return loanRepository.findAll().stream()
                .filter(loan -> loan.getDueDate() != null && loan.getDueDate().isBefore(LocalDateTime.now()))
                .map(loanMapper::toDTO)
                .toList();
    }

    // Возвращает историю всех выдач указанного читателя
    @Override
    @Transactional(readOnly = true)
    public List<LoanDTO> getReaderHistory(Long readerId) {
        return loanRepository.findAll().stream()
                .filter(loan -> loan.getReader() != null && loan.getReader().getId().equals(readerId))
                .map(loanMapper::toDTO)
                .toList();
    }
}