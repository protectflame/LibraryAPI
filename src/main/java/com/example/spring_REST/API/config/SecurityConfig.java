package com.example.spring_REST.API.config;

import com.example.spring_REST.API.security.JwtAuthenticationFilter;
import com.example.spring_REST.API.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsServiceImpl);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // ✅ CORS — разрешаем запросы из браузера (IntelliJ / любой localhost)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*")); // в проде замени на конкретный origin
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ включаем CORS
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(
                        org.springframework.security.config.http.SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // ✅ OPTIONS preflight — разрешаем все без авторизации
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/webjars/**").permitAll()
                        .requestMatchers("/static/**", "/*.html").permitAll() // ✅ admin panel доступен без токена (токен проверяется при запросах)

                        .requestMatchers(HttpMethod.GET, "/api/v1/books/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/books/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/books/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/books/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/readers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/readers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/readers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/readers/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/authors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/authors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/authors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/authors/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/loans/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/loans/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/loans/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/loans/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/loans/**").hasRole("ADMIN")

                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}