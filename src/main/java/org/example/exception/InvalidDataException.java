package org.example.exception;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException() {
        super("Given data is invalid");
    }

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

}
