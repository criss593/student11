package com.example.student.service;

import com.example.student.model.Student;
import com.example.student.repository.StudentJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentJpaRepository repo;

    @InjectMocks
    private StudentService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // =====================
    // CREATE SUCCESS
    // =====================
    @Test
    void create_success() {
        Student student = new Student("123", "Andi", 3.5);

        when(repo.existsById("123")).thenReturn(false);
        when(repo.save(student)).thenReturn(student);

        Student result = service.create(student);

        assertThat(result).isNotNull();
        assertThat(result.getNpm()).isEqualTo("123");
        verify(repo).save(student);
    }

    // =====================
    // CREATE DUPLICATE NPM
    // =====================
    @Test
    void create_duplicate_throws() {
        Student student = new Student("123", "Andi", 3.5);

        when(repo.existsById("123")).thenReturn(true);

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> service.create(student)
        );

        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ex.getReason()).isEqualTo("NPM exists");
    }

    // =====================
    // FIND BY NPM SUCCESS
    // =====================
    @Test
    void findByNpm_success() {
        Student student = new Student("123", "Andi", 3.5);

        when(repo.findById("123")).thenReturn(Optional.of(student));

        Student result = service.findByNpm("123");

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Andi");
    }

    // =====================
    // FIND BY NPM NOT FOUND
    // =====================
    @Test
    void findByNpm_not_found() {

        when(repo.findById("999")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> service.findByNpm("999")
        );

        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
