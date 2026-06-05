package com.library.api.repository;

import com.library.api.model.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
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
