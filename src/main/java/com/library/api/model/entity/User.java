package com.library.api.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

// Сущность пользователя системы, реализует UserDetails для Spring Security
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // Уникальный идентификатор пользователя

    @Column(unique = true, nullable = false)
    private String username;            // Логин для входа

    @Column(nullable = false)
    private String password;            // Хэш пароля

    @Column(unique = true, nullable = false)
    private String email;               // Электронная почта

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;                  // Роль пользователя в системе

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;    // Дата и время регистрации

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Reader reader;              // Связанный профиль читателя

    // Возвращает список прав доступа на основе роли пользователя
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    // Аккаунт не истёк
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Аккаунт не заблокирован
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Учётные данные не истекли
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Аккаунт активен
    @Override
    public boolean isEnabled() {
        return true;
    }
}