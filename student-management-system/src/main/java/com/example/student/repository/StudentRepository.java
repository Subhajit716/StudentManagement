package com.example.student.repository;

import com.example.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Extending JpaRepository gives us save, findById, findAll, deleteById, existsById ...
 * without writing any SQL. Spring creates the implementation at startup.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Spring builds the SQL from the method name
    boolean existsByEmail(String email);

    // "email = ? AND id <> ?" (used when updating, to ignore the student itself)
    boolean existsByEmailAndIdNot(String email, Long id);
}