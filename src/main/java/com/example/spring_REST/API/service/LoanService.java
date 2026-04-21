package com.example.spring_REST.API.service;

import com.example.spring_REST.API.model.dto.LoanDTO;
import com.example.spring_REST.API.model.entity.Loan;

import java.util.List;

public interface LoanService {
    List<LoanDTO> getAll();

    LoanDTO getById(Long id);

    LoanDTO createLoan(LoanDTO loanDTO);

    LoanDTO updateLoan(Long id, LoanDTO loanDTO);

    void deleteLoan(Long id);

    LoanDTO returnLoan(Long id);
    private LoanDTO toDto(Loan loan) {
        return new LoanDTO(
                loan.getId(),
                loan.getBook().getId(),
                loan.getReader().getId(),
                loan.getIssueDate(),
                loan.getDueDate(),
                loan.getReturnDate()
        );
    }
}
