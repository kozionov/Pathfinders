package ru.otus.hw.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Стандартный ответ об ошибке для REST API.
 * Используется для единообразного форматирования ошибок.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    /**
     * Время возникновения ошибки
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    /**
     * HTTP статус код
     */
    private Integer status;

    /**
     * Краткое описание типа ошибки
     */
    private String error;

    /**
     * Подробное сообщение об ошибке
     */
    private String message;

    /**
     * Путь к API endpoint, где произошла ошибка
     */
    private String path;

    /**
     * Ошибки валидации полей (если применимо)
     * Key: имя поля, Value: сообщение об ошибке
     */
    private Map<String, String> validationErrors;
}
