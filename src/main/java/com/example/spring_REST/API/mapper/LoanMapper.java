package com.example.spring_REST.API.mapper;

import com.example.spring_REST.API.model.dto.LoanDTO;
import com.example.spring_REST.API.model.entity.Book;
import com.example.spring_REST.API.model.entity.Loan;
import com.example.spring_REST.API.model.entity.Reader;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public LoanDTO toDto(Loan loan) {
        return new LoanDTO(
                loan.getId(),
                loan.getBook().getId(),
                loan.getReader().getId(),
                loan.getIssueDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                loan.getStatus()
        );
    }

    public Loan toEntity(LoanDTO dto, Book book, Reader reader) {
        if (dto == null) return null;

        Loan loan = new Loan();
        loan.setId(dto.getId());
        loan.setBook(book);
        loan.setReader(reader);
        loan.setIssueDate(dto.getIssueDate());
        loan.setDueDate(dto.getDueDate());
        loan.setReturnDate(dto.getReturnDate());
        loan.setStatus(dto.getStatus());
        return loan;
    }
}