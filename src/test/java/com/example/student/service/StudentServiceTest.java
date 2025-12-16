package com.example.student.service;

import com.example.student.model.Student;
import com.example.student.repository.StudentJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    StudentJpaRepository repo;
    StudentService service;

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(StudentJpaRepository.class);
        service = new StudentService(repo);
    }

    @Test
    void create_success() {
        Student s = new Student("123", "Budi", 3.0);

        when(repo.existsById("123")).thenReturn(false);
        when(repo.save(any(Student.class))).thenReturn(s);

        Student result = service.create(s);

        assertEquals("123", result.getNpm());
        verify(repo).save(s);
    }

    @Test
    void create_duplicate_throws() {
        when(repo.existsById("123")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> service.create(new Student("123", "Budi", 3.0)));
    }

    @Test
    void findByNpm_notFound() {
        when(repo.findById("999")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.findByNpm("999"));
    }
}
