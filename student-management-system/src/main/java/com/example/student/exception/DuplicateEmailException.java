package com.example.student.exception;

/** Thrown when another student already uses the same email. Becomes HTTP 409. */
public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("A student with email '" + email + "' already exists");
    }
}