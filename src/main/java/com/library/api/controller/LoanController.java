package com.library.api.controller;

import com.library.api.model.dto.LoanDTO;
import com.library.api.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
@AllArgsConstructor
@Validated
@Tag(name = "Loans", description = "Управление выдачами и возвратами книг")
public class LoanController {

    private final LoanService loanService;

    @GetMapping
    @Operation(summary = "Получить все выдачи")
    public List<LoanDTO> getAllLoans() {
        return loanService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить выдачу по ID")
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Оформить выдачу книги")
    public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO loanDTO) {
        return ResponseEntity.ok(loanService.create(loanDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить выдачу")
    public ResponseEntity<LoanDTO> updateLoan(@PathVariable Long id, @RequestBody LoanDTO loanDTO) {
        return ResponseEntity.ok(loanService.update(id, loanDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить запись о выдаче")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/return")
    @Operation(summary = "Вернуть книгу")
    public ResponseEntity<LoanDTO> returnLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.returnLoan(id));
    }

    @GetMapping("/active")
    @Operation(summary = "Активные выдачи")
    public List<LoanDTO> getActiveLoans() {
        return loanService.getActiveLoans();
    }

    @GetMapping("/overdue")
    @Operation(summary = "Просроченные выдачи")
    public List<LoanDTO> getOverdueLoans() {
        return loanService.getOverdueLoans();
    }
}