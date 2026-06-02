package com.library.api.controller;

import com.library.api.model.dto.ReaderDTO;
import com.library.api.service.ReaderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/readers")
@Tag(name = "Readers", description = "Управление читателями: создание, поиск, обновление")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService readerService;

    @GetMapping
    @Operation(summary = "Получение всех читателей")
    public List<ReaderDTO> getAll() {
        return readerService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение всех читателя по ID")
    public ReaderDTO getById(@PathVariable Long id) {
        return readerService.getById(id);
    }

    @PostMapping
    @Operation(summary = d)
    public ReaderDTO create(@RequestBody ReaderDTO readerDTO) {
        return readerService.createReader(readerDTO);
    }

    @PutMapping("/{id}")
    public ReaderDTO update(@PathVariable Long id, @RequestBody ReaderDTO readerDTO) {
        return readerService.updateReader(id, readerDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        readerService.deleteReader(id);
    }
}