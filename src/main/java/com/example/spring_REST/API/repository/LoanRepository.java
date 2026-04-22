package com.example.spring_REST.API.repository;

import com.example.spring_REST.API.model.entity.Loan;
import com.example.spring_REST.API.model.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByReaderId(Long readerId);

    List<Loan> findByReaderIdAndStatus(Long reader_id, LoanStatus status);

    List<Loan> findByBookIdAndStatus(Long book_id, LoanStatus status);

    List<Loan> findByStatus(LoanStatus status);

    List<Loan> findByDueDateBeforeAndReturnDateIsNull(LocalDateTime now);


    @Query("""
    SELECT l FROM Loan l
    WHERE l.status = 'ACTIVE'
    AND l.dueDate < CURRENT_DATE
    """)
    List<Loan> findOverdueLoans();

}
