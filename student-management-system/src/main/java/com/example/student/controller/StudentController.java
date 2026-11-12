package com.example.student.controller;

import com.example.student.dto.StudentRequestDTO;
import com.example.student.dto.StudentResponseDTO;
import com.example.student.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // POST /api/students  ->  201 Created
    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(@Valid @RequestBody StudentRequestDTO request) {
        StudentResponseDTO created = studentService.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/students  ->  200 OK
    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // GET /api/students/5  ->  200 OK (or 404)
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // PUT /api/students/5  ->  200 OK (or 404 / 409)
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long id,
                                                            @Valid @RequestBody StudentRequestDTO request) {
        return ResponseEntity.ok(studentService.updateStudent(id, request));
    }

    // DELETE /api/students/5  ->  204 No Content (or 404)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}