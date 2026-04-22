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

    @Column
    private String name;

    @Column
    private String email;

    private int phone;

    private LocalDate registeredAt = LocalDate.now();

    private String status;

    @OneToMany(mappedBy = "reader", fetch = FetchType.LAZY)
    private List<Loan> loans = new ArrayList<>();



}
