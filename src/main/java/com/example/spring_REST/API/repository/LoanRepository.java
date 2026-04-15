package com.example.spring_REST.API.repository;

import com.example.spring_REST.API.model.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByReaderId(Long readerId);

    List<Loan> findByReaderIdAndStatus(Long readerId, String status);

    List<Loan> findByBookIdAndStatus(Long bookId, String status);

    List<Loan> findByStatus(String status, Pageable pageable);

    @Query("""
    SELECT l FROM Loan l
    WHERE l.status = 'ACTIVE'
    AND l.dueDate < CURRENT_DATE
    """)
    List<Loan> findOverdueLoans();


}
