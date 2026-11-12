package com.example.student.exception;

/** Thrown when a student with the requested id does not exist. Becomes HTTP 404. */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}