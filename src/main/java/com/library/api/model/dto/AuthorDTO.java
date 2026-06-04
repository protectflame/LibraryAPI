package com.library.api.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO implements HasId {
    private Long id;
    @NotBlank(message = "Имя обязательно")
    @Size(min = 2, max = 50, message = "Имя должно быть 2-50 символов")
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Past(message = "Дата рождение не может быть в прошлом ")
    private LocalDate birthDate;

}
