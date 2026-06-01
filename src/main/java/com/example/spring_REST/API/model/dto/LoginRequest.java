package com.example.spring_REST.API.model.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}