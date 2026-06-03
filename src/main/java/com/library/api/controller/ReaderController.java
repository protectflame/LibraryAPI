package com.library.api.controller;

import com.library.api.model.dto.ReaderDTO;
import com.library.api.service.ReaderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/readers")
@AllArgsConstructor
@Validated
@Tag(name = "Readers", description = "Управление читателями: создание, поиск, обновление")
public class ReaderController {

    private final ReaderService readerService;

    @GetMapping
    public List<ReaderDTO> getAllReaders() {
        return readerService.getAll();
    }

    @GetMapping("/{id}")
    public ReaderDTO getReaderById(@PathVariable Long id) {
        return readerService.getById(id);
    }

    @PostMapping
    public ReaderDTO createdReaders(@RequestBody ReaderDTO readerDTO) {
        return readerService.create(readerDTO);
    }

    @PutMapping("/{id}")
    public ReaderDTO updateReader(@PathVariable @Min(1) Long id, @RequestBody ReaderDTO readerDTO) {
        return readerService.update(id, readerDTO);
    }

    @DeleteMapping("/{id}")
    public void removeReader(@PathVariable Long id) {
        readerService.remove(id);
    }
}