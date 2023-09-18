package org.example.exception;

public class ImplementationNotFoundException extends RuntimeException {

    public ImplementationNotFoundException(Class<?> clazz) {
        super("Implementation for interface %s not found".formatted(clazz.getName()));
    }

    public ImplementationNotFoundException(String message) {
        super(message);
    }

    public ImplementationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
