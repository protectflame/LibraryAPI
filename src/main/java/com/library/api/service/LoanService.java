package com.library.api.service;

import com.library.api.model.dto.LoanDTO;

import java.util.List;

// Сервис для управления выдачами книг
public interface LoanService {

    // Возвращает список всех выдач
    List<LoanDTO> getAll();

    // Возвращает выдачу по идентификатору
    LoanDTO getById(Long id);

    // Создаёт новую выдачу книги
    LoanDTO create(LoanDTO loanDTO);

    // Обновляет данные выдачи по идентификатору
    LoanDTO update(Long id, LoanDTO loanDTO);

    // Удаляет выдачу по идентификатору
    void remove(Long id);

    // Оформляет возврат книги по идентификатору выдачи
    LoanDTO returnLoan(Long id);

    // Возвращает список активных выдач
    List<LoanDTO> getActiveLoans();

    // Возвращает список просроченных выдач
    List<LoanDTO> getOverdueLoans();

    // Возвращает историю выдач читателя по его идентификатору
    List<LoanDTO> getReaderHistory(Long readerId);
}


