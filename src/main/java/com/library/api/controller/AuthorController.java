package com.library.api.controller;

import com.library.api.model.dto.AuthorDTO;
import com.library.api.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public Page<AuthorDTO> getAll(Pageable pageable){
        return authorService.getAllAuthors(pageable);
    }

    @PostMapping
    @Operation(summary = "Создание автора")
    public ResponseEntity<AuthorDTO> create(@RequestBody AuthorDTO authorDTO){
        AuthorDTO createAuthor = authorService.createAuthor(authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAuthor);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление автора по ID")
    public ResponseEntity<AuthorDTO> remove(@PathVariable Long id){
        authorService.removeAuthor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
