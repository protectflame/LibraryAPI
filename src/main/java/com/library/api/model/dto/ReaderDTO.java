package com.library.api.model.dto;

import com.library.api.model.entity.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// DTO для передачи данных о читателе
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReaderDTO implements HasId {
    private Long id;                  // Уникальный идентификатор читателя
    private String name;              // Имя читателя
    private String email;             // Электронная почта
    private String phone;             // Номер телефона
    private LocalDate registeredAt;   // Дата регистрации
    private LoanStatus status;        // Статус читателя
}