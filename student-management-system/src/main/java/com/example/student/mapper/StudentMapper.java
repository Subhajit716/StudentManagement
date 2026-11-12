package com.example.student.mapper;

import com.example.student.dto.StudentRequestDTO;
import com.example.student.dto.StudentResponseDTO;
import com.example.student.entity.Student;
import org.springframework.stereotype.Component;

/**
 * Converts between Entity (database object) and DTO (JSON object).
 */
@Component
public class StudentMapper {

    public Student toEntity(StudentRequestDTO dto) {
        Student student = new Student();
        copyFields(dto, student);
        return student;
    }

    // Used by update: copy the new values onto an entity that already exists in the database
    public void updateEntity(Student student, StudentRequestDTO dto) {
        copyFields(dto, student);
    }

    public StudentResponseDTO toResponse(Student student) {
        return new StudentResponseDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getPhoneNumber(),
                student.getDepartment(),
                student.getCourse(),
                student.getGender(),
                student.getDateOfBirth()
        );
    }

    private void copyFields(StudentRequestDTO dto, Student student) {
        student.setFirstName(dto.firstName().trim());
        student.setLastName(dto.lastName().trim());
        student.setEmail(dto.email().trim());
        student.setPhoneNumber(dto.phoneNumber().trim());
        student.setDepartment(dto.department().trim());
        student.setCourse(dto.course().trim());
        student.setGender(dto.gender());
        student.setDateOfBirth(dto.dateOfBirth());
    }
}