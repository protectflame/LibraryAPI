package com.library.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// DTO для возврата результата аутентификации
@Data
@AllArgsConstructor
public class LoginResponse {
    private String token; // JWT-токен
}