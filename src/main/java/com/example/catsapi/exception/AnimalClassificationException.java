package com.example.catsapi.exception;

public class AnimalClassificationException extends RuntimeException {
    public AnimalClassificationException(String message) {
        super(message);
    }

    public AnimalClassificationException(String message, Throwable cause) {
        super(message, cause);
    }
}

