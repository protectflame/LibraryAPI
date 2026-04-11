package com.example.spring_REST.API.repository;

import com.example.spring_REST.API.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
