package com.library.api.model.dto;

import com.library.api.model.entity.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// DTO для передачи данных о выдаче книги
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO implements HasId {
    private Long id;          // Уникальный идентификатор выдачи
    private Long bookId;      // Идентификатор книги
    private Long readerId;    // Идентификатор читателя
    private LocalDateTime issueDate;   // Дата выдачи
    private LocalDateTime dueDate;     // Дата возврата по плану
    private LocalDateTime returnDate;  // Фактическая дата возврата
    private LoanStatus status;         // Статус выдачи
}