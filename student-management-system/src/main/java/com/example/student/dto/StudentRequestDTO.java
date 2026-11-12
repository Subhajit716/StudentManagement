package com.example.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * DTO = Data Transfer Object. This is the JSON the client SENDS to us (create / update).
 * It has no id, because the database generates it.
 */
public record StudentRequestDTO(

        @NotBlank(message = "First name is required")
        @Size(max = 50, message = "First name can have at most 50 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 50, message = "Last name can have at most 50 characters")
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be a valid email address")
        @Size(max = 100, message = "Email can have at most 100 characters")
        String email,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^\\+?[0-9]{10,15}$",
                message = "Phone number must have 10 to 15 digits (optional leading +)")
        String phoneNumber,

        @NotBlank(message = "Department is required")
        @Size(max = 100, message = "Department can have at most 100 characters")
        String department,

        @NotBlank(message = "Course is required")
        @Size(max = 100, message = "Course can have at most 100 characters")
        String course,

        @NotBlank(message = "Gender is required")
        @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female or Other")
        String gender,

        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth
) {
}