package com.library.api.service;

import com.library.api.model.dto.LoginRequest;
import com.library.api.model.dto.LoginResponse;
import com.library.api.model.entity.Role;
import com.library.api.model.entity.User;
import com.library.api.repository.UserRepository;
import com.library.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.READER);
        return userRepository.save(user);
    }


    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Неверный username или password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Неверный username или password");
        }

        String token = jwtService.generateToken(user);
        return new LoginResponse(token);
    }
}