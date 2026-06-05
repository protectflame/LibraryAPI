package com.library.api.repository;

import com.library.api.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {



        // ✅ Для обычного списка — @EntityGraph (НЕ JOIN FETCH!)
        @EntityGraph(attributePaths = {"authors"})
        @Query("SELECT b FROM Book b")
        Page<Book> findAllWithAuthors(Pageable pageable);
    /**
     * Поиск с фильтрами.
     * :param - это параметр запроса. Если он null, условие игнорируется (благодаря OR ... IS NULL)
     */
    // ✅ ПРАВИЛЬНЫЙ запрос с CAST для PostgreSQL
    @Query(value =
            "SELECT DISTINCT b FROM Book b " +
                    "LEFT JOIN b.authors a " +
                    "WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CAST(CONCAT('%', :title, '%') AS string))) " +
                    "AND (:genre IS NULL OR LOWER(b.genre) = LOWER(CAST(:genre AS string))) " +
                    "AND (:authorName IS NULL OR LOWER(a.firstName) LIKE LOWER(CAST(CONCAT('%', :authorName, '%') AS string)) " +
                    "                     OR LOWER(a.lastName) LIKE LOWER(CAST(CONCAT('%', :authorName, '%') AS string))) " +
                    "AND (:available IS NULL OR (:available = true AND b.availableCopies > 0))",
            countQuery =
                    "SELECT COUNT(DISTINCT b) FROM Book b " +
                            "LEFT JOIN b.authors a " +
                            "WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CAST(CONCAT('%', :title, '%') AS string))) " +
                            "AND (:genre IS NULL OR LOWER(b.genre) = LOWER(CAST(:genre AS string))) " +
                            "AND (:authorName IS NULL OR LOWER(a.firstName) LIKE LOWER(CAST(CONCAT('%', :authorName, '%') AS string)) " +
                            "                     OR LOWER(a.lastName) LIKE LOWER(CAST(CONCAT('%', :authorName, '%') AS string))) " +
                            "AND (:available IS NULL OR (:available = true AND b.availableCopies > 0))")
    Page<Book> findWithFilters(
            @Param("title") String title,
            @Param("genre") String genre,
            @Param("authorName") String authorName,
            @Param("available") Boolean available,
            Pageable pageable
    );
    boolean existsByIsbn(String isbn);
    @Query("SELECT DISTINCT b FROM Book b JOIN b.authors a WHERE a.id = :authorId")
    Page<Book> findByAuthorsId(Long authorId, Pageable pageable);
    boolean existsByAuthorsId(Long authorId);

}
