package com.example.student.dto;

import java.time.LocalDate;

/**
 * DTO for the JSON we SEND back to the client. It includes the generated id.
 */
public record StudentResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String department,
        String course,
        String gender,
        LocalDate dateOfBirth
) {
}