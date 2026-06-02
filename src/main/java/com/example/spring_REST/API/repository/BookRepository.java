package com.example.spring_REST.API.repository;

import com.example.spring_REST.API.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByGenre(String genre);

    /**
     * Поиск с фильтрами.
     * :param - это параметр запроса. Если он null, условие игнорируется (благодаря OR ... IS NULL)
     */
    @Query("SELECT b FROM Book b LEFT JOIN b.authors a " +
            "WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:genre IS NULL OR LOWER(b.genre) = LOWER(:genre)) " +
            "AND (:authorName IS NULL OR LOWER(a.firstName) LIKE LOWER(CONCAT('%', :authorName, '%')) " +
            "                     OR LOWER(a.lastName) LIKE LOWER(CONCAT('%', :authorName, '%'))) " +
            "AND (:available IS NULL OR (:available = true AND b.availableCopies > 0))")
    Page<Book> findWithFilters(@Param("title") String title,
                               @Param("genre") String genre,
                               @Param("authorName") String authorName,
                               @Param("available") Boolean available,
                               Pageable pageable);

    @Query("select b.id from Book b")
    Page<Long> findBookIds(Pageable pageable);

    @Query("select distinct b from Book b left join fetch b.authors where b.id in :ids")
    List<Book> findAllByIdInWithAuthors(@Param("ids") List<Long> ids);
}
