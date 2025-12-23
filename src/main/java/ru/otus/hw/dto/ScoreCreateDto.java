package ru.otus.hw.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для создания оценки.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreCreateDto {

    @NotNull(message = "ID ученика обязателен")
    private Long studentId;

    @NotNull(message = "ID занятия обязателен")
    private Long logId;

    @NotNull(message = "Оценка обязательна")
    @Min(value = 1, message = "Оценка должна быть не менее 1")
    @Max(value = 5, message = "Оценка должна быть не более 5")
    private Integer score;

    private String comment;
}
