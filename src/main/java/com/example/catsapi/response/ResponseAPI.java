package com.example.catsapi.response;

public class ResponseAPI<T> {
    private String message;
    private T data;

    // Constructor
    public ResponseAPI(String message, T data) {
        this.message = message;
        this.data = data;
    }

    // Static factory method for success responses
    public static <T> ResponseAPI<T> success(T data) {
        return new ResponseAPI<>("Operation successful", data);
    }

    // Static factory method for error responses
    public static <T> ResponseAPI<T> error(String errorMessage) {
        return new ResponseAPI<>(errorMessage, null);
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
