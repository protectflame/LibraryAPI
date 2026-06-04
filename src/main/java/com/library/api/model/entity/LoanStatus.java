package com.library.api.model.entity;

// Статус выдачи книги
public enum LoanStatus {
    ACTIVE,   // Книга на руках у читателя
    RETURNED, // Книга возвращена
    OVERDUE   // Срок возврата истёк
}