package com.example.student.repository;

import com.example.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentJpaRepository
        extends JpaRepository<Student, String> {

    List<Student> findByNameContainingIgnoreCase(String name);
}
