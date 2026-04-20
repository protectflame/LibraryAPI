package com.example.spring_REST.API.model.dto;

public class ReaderDTO {
    private Long id;
    private String name;
    private String email;
    private int phone;

    public ReaderDTO(Long id, String name, String email, int phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public ReaderDTO(Long id, String name, String email) {

    }
}
