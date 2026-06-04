package com.library.api.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// Сущность, представляющая выдачу книги читателю
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;              // Уникальный идентификатор выдачи

    @ManyToOne(optional = false)
    @JoinColumn
    private Book book;            // Выданная книга

    @ManyToOne(optional = false)
    @JoinColumn
    private Reader reader;        // Читатель, получивший книгу

    private LocalDateTime issueDate;    // Дата выдачи
    private LocalDateTime dueDate;      // Дата возврата по плану
    private LocalDateTime returnDate;   // Фактическая дата возврата

    @Enumerated(EnumType.STRING)
    private LoanStatus status;          // Статус выдачи
}