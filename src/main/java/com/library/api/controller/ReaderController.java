package com.library.api.controller;

import com.library.api.model.dto.ReaderDTO;
import com.library.api.service.ReaderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/readers")
@AllArgsConstructor
@Validated
@Tag(name = "Readers", description = "Управление читателями библиотеки")
public class ReaderController {

    private final ReaderService readerService;

    @GetMapping
    @Operation(summary = "Получить всех читателей")
    public List<ReaderDTO> getAllReaders() {
        return readerService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить читателя по ID")
    public ResponseEntity<ReaderDTO> getReaderById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(readerService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Создать читателя")
    public ResponseEntity<ReaderDTO> createReader(@RequestBody ReaderDTO readerDTO) {
        return ResponseEntity.ok(readerService.create(readerDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные читателя")
    public ResponseEntity<ReaderDTO> updateReader(
            @PathVariable @Min(1) Long id,
            @RequestBody ReaderDTO readerDTO) {
        return ResponseEntity.ok(readerService.update(id, readerDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить читателя")
    public ResponseEntity<Void> deleteReader(@PathVariable Long id) {
        readerService.remove(id);
        return ResponseEntity.noContent().build();
    }
}