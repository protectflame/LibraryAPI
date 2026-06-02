package com.library.api.controller;

import com.library.api.model.dto.LoanDTO;
import com.library.api.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
@Tag(name = "Loans",description = "Управление выдачами: Создание, возвращение, удаление и получение выдач ")
public class LoanController {

    private final LoanService loanService;

    @GetMapping
    @Operation(summary = "Получение всех выдач")
    public List<LoanDTO> getAll() {
        return loanService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение выдачи по ID")
    public LoanDTO getById(@PathVariable Long id) {
        return loanService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Создание выдачи")
    public LoanDTO create(@RequestBody LoanDTO loanDTO) {
        return loanService.createLoan(loanDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление выдачи")
    public LoanDTO update(@PathVariable Long id, @RequestBody LoanDTO loanDTO) {
        return loanService.updateLoan(id, loanDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление выдачи")
    public void delete(@PathVariable Long id) {
        loanService.deleteLoan(id);
    }

    @PostMapping("/{id}/return")
    @Operation(summary = "Возвращение книги")
    public LoanDTO returnLoan(@PathVariable Long id) {
        return loanService.returnLoan(id);
    }

    @GetMapping("/active")
    @Operation(summary = "Получение активных выдач")
    public List<LoanDTO> getActiveLoans() {
        return loanService.getActiveLoans();
    }

    @GetMapping("/overdue")
    @Operation(summary = "Получение просроченных выдач")
    public List<LoanDTO> getOverdueLoans() {
        return loanService.getOverdueLoans();
    }

    @GetMapping("/reader/{readerId}")
    @Operation(summary = "Получение истории чтение пользователя ")
    public List<LoanDTO> getReaderHistory(@PathVariable Long readerId) {
        return loanService.getReaderHistory(readerId);
    }
}