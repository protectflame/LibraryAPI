package com.example.spring_REST.API.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReaderDTO {
    private Long id;
    private String name;
    private String email;
    private String  phone;


}
