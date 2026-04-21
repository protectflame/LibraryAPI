package com.example.spring_REST.API.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {
    private Long id;
    private Long bookId;
    private Long readerId;
    private LocalDateTime issueDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;


    public LoanDTO(Long id, Long bookId, Long readerId, LocalDateTime issueDate, LocalDateTime dueDate, LocalDateTime returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.readerId = readerId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }
}