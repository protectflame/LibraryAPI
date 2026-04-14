package com.example.spring_REST.API.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Size(min = 3,max = 255,message = "Количество символов не должно быть меньше 3 и больше 255")
    private String title;

    private String isbn;

    @NotBlank(message = "Описание обязательно")
    @Size(min = 3,max = 1000,message = "Количество символов не должно быть меньше 3 и больше 1000")
    private String descrition;

    private LocalDate publishYear;

    @Size(min = 3,max = 50)
    private String genre;

    private Long totalCopies;

    private boolean availableCopies;

    private LocalDateTime createdAt;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "a_id")
    )
    private Set<Author> authors = new HashSet<>();

}
