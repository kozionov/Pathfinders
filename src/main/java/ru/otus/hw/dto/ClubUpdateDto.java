package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для обновления информации о клубе.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubUpdateDto {

    @NotNull(message = "ID клуба обязателен")
    private Long id;

    @NotBlank(message = "Название клуба не может быть пустым")
    @Size(min = 3, max = 100, message = "Название клуба должно содержать от 3 до 100 символов")
    private String name;

    @NotBlank(message = "Город не может быть пустым")
    @Size(min = 2, max = 50, message = "Название города должно содержать от 2 до 50 символов")
    private String city;
}
