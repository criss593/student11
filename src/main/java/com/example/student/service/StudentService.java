package com.example.student.service;

import com.example.student.model.Student;
import com.example.student.repository.StudentJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StudentService {

    private final StudentJpaRepository repo;

    public StudentService(StudentJpaRepository repo) {
        this.repo = repo;
    }

    public Student create(Student student) {
        if (repo.existsById(student.getNpm())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "NPM exists"
            );
        }
        return repo.save(student);
    }

    public List<Student> getAll() {
        return repo.findAll();
    }

    public Student findByNpm(String npm) {
        return repo.findById(npm)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Student not found"
                        )
                );
    }

    public Student update(String npm, Student updated) {
        Student existing = findByNpm(npm);
        existing.setName(updated.getName());
        existing.setGpa(updated.getGpa());
        return repo.save(existing);
    }

    public void delete(String npm) {
        if (!repo.existsById(npm)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Student not found"
            );
        }
        repo.deleteById(npm);
    }
}
