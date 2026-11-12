package com.example.student.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

/** The JSON body returned for every error, so the frontend always sees the same shape. */
public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        Map<String, String> fieldErrors
) {

    public static ApiError of(HttpStatus status, String message) {
        return new ApiError(LocalDateTime.now(), status.value(), status.getReasonPhrase(), message, Map.of());
    }

    public static ApiError of(HttpStatus status, String message, Map<String, String> fieldErrors) {
        return new ApiError(LocalDateTime.now(), status.value(), status.getReasonPhrase(), message, fieldErrors);
    }
}