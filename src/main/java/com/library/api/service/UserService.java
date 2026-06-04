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

    // Метод для регистрации нового пользователя
    public User register(User user) {
        // Шифруем пароль перед сохранением в базу данных
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Устанавливаем роль по умолчанию (READER)
        user.setRole(Role.READER);

        // Сохраняем пользователя и возвращаем обновленный объект
        return userRepository.save(user);
    }

    // Метод для аутентификации пользователя и выдачи JWT-токена
    public LoginResponse login(LoginRequest request) {
        // Ищем пользователя по имени. Если не найден, выбрасываем ошибку
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Неверный username или password"));

        // Проверяем, совпадает ли введенный пароль с зашифрованным паролем в базе
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Неверный username или password");
        }

        // Генерируем JWT-токен для успешно аутентифицированного пользователя
        String token = jwtService.generateToken(user);

        // Возвращаем ответ, содержащий токен
        return new LoginResponse(token);
    }
}