package ru.otus.hw.exception;

/**
 * Исключение для кастомной валидации бизнес-логики.
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
