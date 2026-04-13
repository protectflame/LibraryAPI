package com.example.spring_REST.API.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reader {
    @Id
    private Long id;
    String name;
    String email;
    int phone;
    List registeredAt;
    String status;


}
