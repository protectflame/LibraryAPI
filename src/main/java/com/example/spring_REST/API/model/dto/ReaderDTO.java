package com.example.spring_REST.API.model.dto;

public class ReaderDTO {
    private Long id;
    private String name;
    private String email;

    public ReaderDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
