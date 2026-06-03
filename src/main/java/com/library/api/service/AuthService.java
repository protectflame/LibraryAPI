package com.library.api.service;

import com.library.api.model.entity.Reader;
import com.library.api.model.entity.Role;
import com.library.api.model.entity.User;
import com.library.api.repository.ReaderRepository;
import com.library.api.repository.UserRepository;
import com.library.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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