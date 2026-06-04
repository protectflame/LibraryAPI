package com.library.api.service;

import com.library.api.model.dto.ReaderDTO;

import java.util.List;

// Сервис для управления читателями
public interface ReaderService {

    // Создаёт нового читателя
    ReaderDTO create(ReaderDTO readerDto);

    // Обновляет данные читателя по идентификатору
    ReaderDTO update(Long id, ReaderDTO readerDto);

    // Возвращает читателя по идентификатору
    ReaderDTO getById(Long id);

    // Возвращает список всех читателей
    List<ReaderDTO> getAll();

    // Удаляет читателя по идентификатору
    void remove(Long id);
}