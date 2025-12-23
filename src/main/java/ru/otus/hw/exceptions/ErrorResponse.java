package ru.otus.hw.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Унифицированный формат ответа об ошибке.
 * Содержит всю необходимую информацию для клиента о возникшей ошибке.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    
    /**
     * Временная метка возникновения ошибки.
     */
    private LocalDateTime timestamp;
    
    /**
     * HTTP статус код ошибки.
     */
    private int status;
    
    /**
     * Краткое описание типа ошибки.
     */
    private String error;
    
    /**
     * Подробное сообщение об ошибке для пользователя.
     */
    private String message;
    
    /**
     * Путь API endpoint, где произошла ошибка.
     */
    private String path;
    
    /**
     * Детальная информация об ошибках валидации (если применимо).
     * Ключ - название поля, значение - сообщение об ошибке.
     */
    private Map<String, String> validationErrors;
}