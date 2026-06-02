package com.library.api.model.dto;

import com.library.api.model.entity.LoanStatus;
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