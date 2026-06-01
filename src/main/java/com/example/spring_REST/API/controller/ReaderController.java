package com.example.spring_REST.API.controller;

import com.example.spring_REST.API.model.dto.ReaderDTO;
import com.example.spring_REST.API.service.ReaderService;
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
    public List<ReaderDTO> getAll() {
        return readerService.getAll();
    }

    @GetMapping("/{id}")
    public ReaderDTO getById(@PathVariable Long id) {
        return readerService.getById(id);
    }

    @PostMapping
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