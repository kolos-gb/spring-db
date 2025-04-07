package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAddStudent() {
        Student student = new Student(null, "Harry Potter", 15, null);
        ResponseEntity<Student> response = restTemplate.postForEntity("/students", student, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Harry Potter");
        assertThat(response.getBody().getAge()).isEqualTo(15);
    }

    @Test
    public void testGetStudent() {
        Student student = new Student(null, "Harry Potter", 15, null);
        ResponseEntity<Student> response = restTemplate.postForEntity("/students", student, Student.class);
        Long studentId = response.getBody().getId();
        ResponseEntity<Student> getResponse = restTemplate.getForEntity("/students/" + studentId, Student.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getName()).isEqualTo("Harry Potter");
    }

    @Test
    public void testDeleteStudent() {
        Student student = new Student(null, "Ron Weasley", 16, null);
        ResponseEntity<Student> createResponse = restTemplate.postForEntity("/students", student, Student.class);
        Long studentId = createResponse.getBody().getId();
        restTemplate.delete("/students/" + studentId);
        ResponseEntity<Student> getResponse = restTemplate.getForEntity("/students/" + studentId, Student.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND); // Ожидаем 404
    }

    private URI createUrl(String path) {
        return URI.create("http://localhost:" + port + path);
    }
}
