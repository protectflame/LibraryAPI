package com.example.spring_REST.API.service;

import com.example.spring_REST.API.model.dto.LoanDTO;

import java.util.List;

public interface LoanService {
    List<LoanDTO> getAll();

    LoanDTO getById(Long id);

    LoanDTO createLoan(LoanDTO loanDTO);

    LoanDTO updateLoan(Long id, LoanDTO loanDTO);

    void deleteLoan(Long id);

    LoanDTO returnLoan(Long id);

    List<LoanDTO> getActiveLoans();

    List<LoanDTO> getOverdueLoans();

    List<LoanDTO> getReaderHistory(Long readerId);

}
