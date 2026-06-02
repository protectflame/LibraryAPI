package com.library.api.repository;

import com.library.api.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    public Author findByFirstName(String name);
}
