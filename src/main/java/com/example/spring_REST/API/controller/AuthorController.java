package com.example.spring_REST.API.controller;

import com.example.spring_REST.API.model.dto.AuthorDTO;
import com.example.spring_REST.API.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
@Tag(name = "Authors", description = "Управление авторами: создание, удаление, обновление")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    @Operation(summary = "Получение всех авторов")
    public Page<AuthorDTO> authorDTOList(Pageable pageable) {
        return authorService.getAllAuthors(pageable);
    }

    @PostMapping
    @Operation(summary = "Создание автора")
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
        AuthorDTO createAuthor = authorService.createAuthor(authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAuthor);
    }

    @PutMapping
    @Operation(
            summary = "Обновление автора",
            description = "Передавайте валидный ID"
    )
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorDTO dto) {
        return ResponseEntity.ok(authorService.updateAuthor(id, dto));
    }
}
