package com.example.student.integration;

import com.example.student.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void create_get_delete_flow() {

        Student student = new Student("123", "Andi", 3.5);

        // CREATE
        ResponseEntity<Student> createResponse =
                restTemplate.postForEntity("/api/students", student, Student.class);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // GET
        ResponseEntity<Student> getResponse =
                restTemplate.getForEntity("/api/students/123", Student.class);

        assertThat(getResponse.getBody().getName()).isEqualTo("Andi");

        // DELETE
        restTemplate.delete("/api/students/123");

        // GET AFTER DELETE
        ResponseEntity<String> afterDelete =
                restTemplate.getForEntity("/api/students/123", String.class);

        assertThat(afterDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
