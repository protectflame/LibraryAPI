package com.library.api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// Сервис для работы с JWT-токенами
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;          // Секретный ключ для подписи токена

    @Value("${jwt.expiration:86400000}")
    private long jwtExpiration;        // Время жизни токена в миллисекундах (по умолчанию 24 часа)

    // Возвращает ключ для подписи токена на основе секретного ключа
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // Извлекает все claims из токена
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Извлекает имя пользователя из токена
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Извлекает дату истечения токена
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // Генерирует токен для пользователя без дополнительных claims
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Генерирует токен для пользователя с дополнительными claims
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Проверяет валидность токена: совпадение имени пользователя и срок действия
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Проверяет, истёк ли срок действия токена
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}