package com.example.spring_REST.API.repository;

import com.example.spring_REST.API.model.entity.Reader;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    Optional<Reader> findByEmail(String email);

    Optional<Reader> findByPhone(String phone);

    List<Reader> findByNameContainingIgnoreCase(String name);

    Page<Reader> findAll(Pageable pageable);

    Page<Reader> findByStatus(String status, Pageable pageable);


    @Query("SELECT DISTINCT r FROM Reader r " +
            "JOIN FETCH r.loans l WHERE l.status = 'ACTIVE' AND l.dueDate < CURRENT_DATE")
    Page<Reader> findReadersWithOverdueBooks(Pageable pageable);

}
