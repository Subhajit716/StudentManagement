package com.example.student.service;

import com.example.student.dto.StudentRequestDTO;
import com.example.student.dto.StudentResponseDTO;
import com.example.student.entity.Student;
import com.example.student.exception.DuplicateEmailException;
import com.example.student.exception.ResourceNotFoundException;
import com.example.student.mapper.StudentMapper;
import com.example.student.repository.StudentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    // Constructor injection: Spring passes in the repository and mapper for us
    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    @Transactional
    public StudentResponseDTO createStudent(StudentRequestDTO request) {
        if (studentRepository.existsByEmail(request.email().trim())) {
            throw new DuplicateEmailException(request.email().trim());
        }
        Student saved = studentRepository.save(studentMapper.toEntity(request));
        return studentMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAll(Sort.by("id")).stream()
                .map(studentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentById(Long id) {
        return studentMapper.toResponse(findStudentOrThrow(id));
    }

    @Override
    @Transactional
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO request) {
        Student student = findStudentOrThrow(id); // 404 if it does not exist
        if (studentRepository.existsByEmailAndIdNot(request.email().trim(), id)) {
            throw new DuplicateEmailException(request.email().trim());
        }
        studentMapper.updateEntity(student, request);
        Student saved = studentRepository.save(student);
        return studentMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    private Student findStudentOrThrow(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }
}