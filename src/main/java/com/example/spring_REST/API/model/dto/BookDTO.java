package com.example.spring_REST.API.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookDTO {
    private Long id;
    private Long availableCopies;
    private LocalDateTime createdAt;
    private List<AuthorDTO> authors;

    @NotBlank(message = "Название обязательно")
    @Size(min = 3, max = 255, message = "Длина от 3 до 255 символов")
    private String title;

    @NotBlank(message = "ISBN обязателен")
    private String isbn;

    @Size(max = 1000, message = "Описание до 1000 символов")
    private String description;

    @PastOrPresent(message = "Дата публикации не может быть в будущем")
    private LocalDate publishYear;

    @Size(max = 50)
    private String genre;

    @Min(value = 1, message = "Количество экземпляров должно быть > 0")
    private Long totalCopies;
}