package com.example.spring_REST.API.repository;

import com.example.spring_REST.API.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
