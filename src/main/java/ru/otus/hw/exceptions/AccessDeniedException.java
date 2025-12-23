package ru.otus.hw.exceptions;

/**
 * Исключение, выбрасываемое при попытке несанкционированного доступа к ресурсу.
 */
public class AccessDeniedException extends RuntimeException {
    
    public AccessDeniedException(String message) {
        super(message);
    }
    
    public AccessDeniedException() {
        super("У вас нет прав для выполнения данной операции");
    }
}