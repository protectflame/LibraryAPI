package com.example.spring_REST.API.controller;


import com.example.spring_REST.API.model.dto.ReaderDTO;
import org.springframework.web.bind.annotation.*;
import com.example.spring_REST.API.service.ReaderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/readers")
public class ReaderController {

    private final ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

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