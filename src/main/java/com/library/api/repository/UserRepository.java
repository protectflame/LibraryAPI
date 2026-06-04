package com.library.api.repository;

import com.library.api.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Репозиторий для работы с пользователями системы
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Возвращает пользователя по логину
    Optional<User> findByUsername(String username);

    // Возвращает пользователя по электронной почте
    Optional<User> findByEmail(String email);

    // Проверяет, существует ли пользователь с указанным логином
    boolean existsByUsername(String username);

    // Проверяет, существует ли пользователь с указанной электронной почтой
    boolean existsByEmail(String email);
}