package com.library.api.controller;

import com.library.api.model.dto.AuthorDTO;
import com.library.api.model.dto.BookDTO;
import com.library.api.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
@Tag(name = "Authors", description = "Управление авторами книг")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    @Operation(summary = "Получить всех авторов", description = "С поддержкой пагинации и сортировки")
    public Page<AuthorDTO> getAllAuthors(@ParameterObject Pageable pageable) {
        return authorService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить автора по ID")
    public AuthorDTO getAuthorById(@PathVariable @Min(1) Long id) {
        return authorService.getById(id);
    }
    @GetMapping("/books/{id}")
    public Page<BookDTO> getBooksByAuthorId (@PathVariable @Min(1) Long id, @ParameterObject Pageable pageable){
        return authorService.getBooksByAuthorId(id,pageable);
    }
    @GetMapping("/search/{query}")
    public Page<AuthorDTO> searchAuthors (@PathVariable String query,Pageable Pageable){
        return authorService.searchByName(query,Pageable);
    }
    @PostMapping
    @Operation(summary = "Создать нового автора")
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        AuthorDTO created = authorService.create(authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить автора")
    public ResponseEntity<AuthorDTO> updateAuthor(
            @PathVariable @Min(1) Long id,
            @Valid @RequestBody AuthorDTO dto) {
        return ResponseEntity.ok(authorService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить автора")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAuthor(@PathVariable @Min(1) Long id) {
        authorService.remove(id);
    }


}