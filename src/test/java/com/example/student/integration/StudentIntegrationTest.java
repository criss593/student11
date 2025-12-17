package com.example.student.integration;

import com.example.student.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentIntegrationTest {
// TODO: cek lagi response code 200/201

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void create_get_delete_flow() {

        // =====================
        // CREATE
        // =====================
        Student student = new Student("123", "Andi", 3.5);

        ResponseEntity<Student> createResponse =
                restTemplate.postForEntity("/api/students", student, Student.class);

        assertThat(createResponse.getStatusCode().value())
                .isIn(200, 201);



        assertThat(createResponse.getBody()).isNotNull();
        assertThat(createResponse.getBody().getNpm())
                .isEqualTo("123");

        // =====================
        // GET
        // =====================
        ResponseEntity<Student> getResponse =
                restTemplate.getForEntity("/api/students/123", Student.class);

        assertThat(createResponse.getStatusCode().value())
                .isIn(200, 201);



        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getName())
                .isEqualTo("Andi");

        // =====================
        // DELETE
        // =====================
        restTemplate.delete("/api/students/123");

        // =====================
        // GET AFTER DELETE
        // =====================
        ResponseEntity<String> afterDelete =
                restTemplate.getForEntity("/api/students/123", String.class);

        assertThat(afterDelete.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
