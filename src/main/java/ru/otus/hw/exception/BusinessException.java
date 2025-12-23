package ru.otus.hw.exception;

/**
 * Исключение для ошибок бизнес-логики.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
