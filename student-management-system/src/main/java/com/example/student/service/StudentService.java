package com.example.student.service;

import com.example.student.dto.StudentRequestDTO;
import com.example.student.dto.StudentResponseDTO;

import java.util.List;

/** The business operations of the application. The controller only depends on this interface. */
public interface StudentService {

    StudentResponseDTO createStudent(StudentRequestDTO request);

    List<StudentResponseDTO> getAllStudents();

    StudentResponseDTO getStudentById(Long id);

    StudentResponseDTO updateStudent(Long id, StudentRequestDTO request);

    void deleteStudent(Long id);
}