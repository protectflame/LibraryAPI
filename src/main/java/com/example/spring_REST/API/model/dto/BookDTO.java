package com.example.spring_REST.API.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookDTO {

        private Long id;

        @NotBlank(message = "Название обязательно")
        @Size(min = 3, max = 255, message = "Длина названия от 3 до 255 символов")
        private String title;

        // ISBN - это уникальный номер каждой книги
        private String isbn;

        @NotBlank(message = "Описание обязательно")
        @Size(min = 3, max = 1000, message = "Длина описания от 3 до 1000 символов")
        private String description;

        @Min(value = 1400, message = "Год издания не может быть раньше 1400")
        private LocalDate publishYear;

        @Size(max = 50)
        private String genre;

        private Long totalCopies;

        private boolean availableCopies;

        private LocalDateTime createdAt;

        private List<AuthorDTO> authors;
}
