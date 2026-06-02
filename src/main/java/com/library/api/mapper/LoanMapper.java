package com.library.api.mapper;

import com.library.api.model.dto.LoanDTO;
import com.library.api.model.entity.Book;
import com.library.api.model.entity.Loan;
import com.library.api.model.entity.Reader;
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