package com.library.api.controller;

import com.library.api.model.dto.LoginRequest;
import com.library.api.model.dto.LoginResponse;
import com.library.api.model.entity.User;
import com.library.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
