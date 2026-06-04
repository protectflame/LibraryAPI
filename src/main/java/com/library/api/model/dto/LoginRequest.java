package com.library.api.model.dto;

import lombok.Data;

// DTO для передачи данных при аутентификации пользователя
@Data
public class LoginRequest {
    private String username; // Имя пользователя
    private String password; // Пароль
}
