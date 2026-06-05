package com.library.api.controller;

import com.library.api.model.dto.LoginRequest;
import com.library.api.model.dto.LoginResponse;
import com.library.api.model.entity.User;
import com.library.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Аутентификация и регистрация пользователей")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Регистрация нового пользователя",
            description = "Создаёт пользователя с ролью READER по умолчанию")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    @Operation(summary = "Вход в систему",
            description = "Аутентификация и получение JWT-токена")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}