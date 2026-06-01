package com.example.spring_REST.API.controller;

import com.example.spring_REST.API.model.dto.LoginRequest;
import com.example.spring_REST.API.model.dto.LoginResponse;
import com.example.spring_REST.API.model.entity.User;
import com.example.spring_REST.API.service.UserService;
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
