package com.example.student.controller;

import com.example.student.model.Student;
import com.example.student.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Student> getAll() {
        return service.getAll();
    }

    @GetMapping("/{npm}")
    public Student getByNpm(@PathVariable String npm) {
        return service.findByNpm(npm);
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        Student created = service.create(student);
        return ResponseEntity
                .created(URI.create("/api/students/" + created.getNpm()))
                .body(created);
    }

    @PutMapping("/{npm}")
    public Student update(@PathVariable String npm,
                          @RequestBody Student student) {
        return service.update(npm, student);
    }

    @DeleteMapping("/{npm}")
    public void delete(@PathVariable String npm) {
        service.delete(npm);
    }
}
