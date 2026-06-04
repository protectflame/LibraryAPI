package com.library.api.model.dto;

// Интерфейс для сущностей, имеющих уникальный идентификатор
public interface HasId {
    // Возвращает уникальный идентификатор сущности
    Long getId();
}