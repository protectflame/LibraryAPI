package com.example.spring_REST.API.repository;

import com.example.spring_REST.API.model.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    public Optional<Author> findByFirstName(String name);
    @Query("""
        select a
        from Author a
        where lower(a.firstName) like lower(concat('%', :query, '%'))
           or lower(a.lastName) like lower(concat('%', :query, '%'))
        """)
    Page<Author> findByFirstNameContainingOrLastNameContaining(
            @Param("query") String query,
            Pageable pageable
    );
    boolean existsById(Long id);

}
