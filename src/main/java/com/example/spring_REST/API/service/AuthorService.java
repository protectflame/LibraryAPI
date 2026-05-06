package com.example.spring_REST.API.service;

import com.example.spring_REST.API.model.dto.AuthorDTO;
import com.example.spring_REST.API.model.dto.BookDTO;

public interface AuthorService {
    public BookDTO createAuthor();
    public AuthorDTO getAuthor();
    public AuthorDTO updateAuthor();
    public BookDTO removeAuthor();

}
