package com.library.api.repository;

import com.library.api.model.entity.Loan;
import com.library.api.model.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

// Репозиторий для работы с выдачами книг
public interface LoanRepository extends JpaRepository<Loan, Long> {

    // Возвращает все выдачи по идентификатору читателя
    List<Loan> findByReaderId(Long readerId);

    void deleteByBookId(Long id);
    boolean existsByBookIdAndStatus(Long bookId, LoanStatus status);

    // Возвращает выдачи читателя с указанным статусом
    List<Loan> findByReaderIdAndStatus(Long reader_id, LoanStatus status);

    // Возвращает выдачи книги с указанным статусом
    List<Loan> findByBookIdAndStatus(Long book_id, LoanStatus status);

    // Возвращает все выдачи с указанным статусом
    List<Loan> findByStatus(LoanStatus status);

    // Возвращает выдачи, у которых истёк срок и книга не возвращена
    List<Loan> findByDueDateBeforeAndReturnDateIsNull(LocalDateTime now);

    // Возвращает все активные выдачи с истёкшим сроком возврата
    @Query("""
            SELECT l FROM Loan l
            WHERE l.status = 'ACTIVE'
            AND l.dueDate < CURRENT_DATE
            """)
    List<Loan> findOverdueLoans();

}