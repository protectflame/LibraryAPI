package com.example.spring_REST.API.model;

import java.util.List;

public class Reader {
    int id;
    String name;
    String email;
    int phone;
    List registeredAt;
    String status;


    public Reader(String name, String email, int phone, List registeredAt, String status) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.registeredAt = registeredAt;
        this.status = status;




    }
}
