package com.example.spring_REST.API.model.dto;

import com.example.spring_REST.API.model.entity.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReaderDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDate registeredAt;
    private LoanStatus status;


}