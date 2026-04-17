package com.example.spring_REST.API.controller;


import com.example.spring_REST.API.model.dto.ReaderDTO;
import org.springframework.web.bind.annotation.*;
import com.example.spring_REST.API.service.ReaderService;

import java.util.List;

@RestController
@RequestMapping("/apiv1/readers")
public class ReaderController {

    private final ReaderService readerService;
    private final ReaderDTO readerDTO;

    public ReaderController(ReaderService readerService, ReaderDTO readerDTO) {
        this.readerService = readerService;
        this.readerDTO = readerDTO;
    }

    @GetMapping
    public List<ReaderDTO> getAll() {
        return readerService.getAll();
    }
    @GetMapping("/{id}")
    public ReaderDTO getById(@PathVariable Long id) {
        return readerService.createReader(readerDTO);
    }
}
