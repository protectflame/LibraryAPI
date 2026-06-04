package com.library.api.controller;

import com.library.api.model.dto.LoanDTO;
import com.library.api.service.LoanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
@AllArgsConstructor
@Validated
@Tag(name="Loans", description = "Управление выдачами: возврат книг, удаление и обновление выдач, получение по ID")
public class LoanController {

    private final LoanService loanService;

    @GetMapping
    public List<LoanDTO> getAllLoan() {
        return loanService.getAll();
    }

    @GetMapping("/{id}")
    public LoanDTO getLoanById(@PathVariable Long id) {
        return loanService.getById(id);
    }

    @PostMapping
    public LoanDTO createdLoan(@RequestBody LoanDTO loanDTO) {
        return loanService.create(loanDTO);
    }

    @PutMapping("/{id}")
    public LoanDTO updateLoan(@PathVariable Long id, @RequestBody LoanDTO loanDTO) {
        return loanService.update(id, loanDTO);
    }

    @DeleteMapping("/{id}")
    public void removeLoan(@PathVariable Long id) {
        loanService.remove(id);
    }

    @PostMapping("/{id}/return")
    public LoanDTO returnLoan(@PathVariable Long id) {
        return loanService.returnLoan(id);
    }

    @GetMapping("/active")
    public List<LoanDTO> getActiveLoans() {
        return loanService.getActiveLoans();
    }

    @GetMapping("/overdue")
    public List<LoanDTO> getOverdueLoans() {
        return loanService.getOverdueLoans();
    }

    @GetMapping("/reader/{readerId}")
    public List<LoanDTO> getReaderHistory(@PathVariable Long readerId) {
        return loanService.getReaderHistory(readerId);
    }
}