package com.library.api.controller;

import com.library.api.model.dto.AuthorDTO;
import com.library.api.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/authors")
@AllArgsConstructor
@Validated
@Tag(name = "Authors", description = "Управление авторами: создание, удаление, обновление")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    @Operation(summary = "Получение всех авторов")
    public Page<AuthorDTO> getAllAuthor(Pageable pageable) {
        return authorService.getAll(pageable);
    }

    @PostMapping
    @Operation(summary = "Создание автора")
    public ResponseEntity<AuthorDTO> createdAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        AuthorDTO createAuthor = authorService.create(authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAuthor);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление автора", description = "Передавайте валидный ID")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable @Min(1) Long id, @Valid @RequestBody AuthorDTO dto) {
        return ResponseEntity.ok(authorService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление автора по ID")
    public ResponseEntity<AuthorDTO> removeAuthor(@PathVariable @Min(1) Long id){
        AuthorDTO removeDTO = authorService.remove(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(removeDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение автора по ID")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok().body(authorService.getById(id));
    }
}
