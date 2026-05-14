package com.example.spring_REST.API.service;

import com.example.spring_REST.API.model.entity.Reader;
import com.example.spring_REST.API.model.entity.Role;
import com.example.spring_REST.API.model.entity.User;
import com.example.spring_REST.API.repository.ReaderRepository;
import com.example.spring_REST.API.repository.UserRepository;
import com.example.spring_REST.API.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ReaderRepository readerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public String registerReader(String name, String email, String phone, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already in use");
        }

        // Создаём User
        User user = User.builder()
                .username(email)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.READER)
                .build();

        user = userRepository.save(user);

        // Создаём Reader и привязываем
        Reader reader = new Reader();
        reader.setName(name);
        reader.setEmail(email);
        reader.setPhone(phone);
        reader.setUser(user);

        readerRepository.save(reader);

        return jwtService.generateToken(user);
    }

    public String authenticate(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtService.generateToken(user);
    }
}