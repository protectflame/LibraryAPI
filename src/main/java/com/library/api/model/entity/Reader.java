package com.library.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Сущность, представляющая читателя библиотеки
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                    // Уникальный идентификатор читателя

    private String name;                                // Имя читателя
    private String email;                               // Электронная почта
    private String phone;                               // Номер телефона
    private LocalDate registeredAt = LocalDate.now();   // Дата регистрации

    @Enumerated(EnumType.STRING)
    private LoanStatus status;                          // Статус читателя

    @OneToMany(mappedBy = "reader", fetch = FetchType.LAZY)
    private List<Loan> loans = new ArrayList<>();       // Список выдач книг читателю

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private User user;                                  // Связанный пользователь системы
}