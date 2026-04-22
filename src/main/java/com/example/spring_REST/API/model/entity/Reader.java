package com.example.spring_REST.API.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String phone;
    private LocalDate registeredAt = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @OneToMany(mappedBy = "reader", fetch = FetchType.LAZY)
    private List<Loan> loans = new ArrayList<>();
}