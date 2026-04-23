package com.example.spring_REST.API.controller;

import com.example.spring_REST.API.model.dto.LoanDTO;
import com.example.spring_REST.API.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public List<LoanDTO> getAll() {
        return loanService.getAll();
    }

    @GetMapping("/{id}")
    public LoanDTO getById(@PathVariable Long id) {
        return loanService.getById(id);
    }

    @PostMapping
    public LoanDTO create(@RequestBody LoanDTO loanDTO) {
        return loanService.createLoan(loanDTO);
    }

    @PutMapping("/{id}")
    public LoanDTO update(@PathVariable Long id, @RequestBody LoanDTO loanDTO) {
        return loanService.updateLoan(id, loanDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        loanService.deleteLoan(id);
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