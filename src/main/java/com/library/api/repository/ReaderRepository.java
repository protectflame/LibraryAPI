package com.library.api.repository;

import com.library.api.model.entity.Reader;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

// Репозиторий для работы с читателями
public interface ReaderRepository extends JpaRepository<Reader, Long> {

    // Возвращает читателя по электронной почте
    Optional<Reader> findByEmail(String email);

    // Возвращает читателя по номеру телефона
    Optional<Reader> findByPhone(String phone);

    // Возвращает список читателей, имя которых содержит указанную строку (без учёта регистра)
    List<Reader> findByNameContainingIgnoreCase(String name);

    // Возвращает всех читателей с поддержкой пагинации
    Page<Reader> findAll(Pageable pageable);

    // Возвращает читателей с указанным статусом с поддержкой пагинации
    Page<Reader> findByStatus(String status, Pageable pageable);

    // Возвращает читателей, у которых есть активные выдачи с истёкшим сроком возврата
    @Query("SELECT DISTINCT r FROM Reader r " +
            "JOIN FETCH r.loans l WHERE l.status = 'ACTIVE' AND l.dueDate < CURRENT_DATE")
    Page<Reader> findReadersWithOverdueBooks(Pageable pageable);

}