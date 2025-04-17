package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAddStudent() {
        Student student = new Student(1L, "Harry Potter", 15, null);
        ResponseEntity<Student> response = restTemplate.postForEntity("/students", student, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Harry Potter");
        assertThat(response.getBody().getAge()).isEqualTo(15);
    }

    @Test
    public void testGetStudent() {
        Student student = new Student(1L, "Harry Potter", 15, null);
        restTemplate.postForEntity("/students", student, Student.class);
        ResponseEntity<Student> response = restTemplate.getForEntity("/students/1", Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Harry Potter");
        assertThat(response.getBody().getAge()).isEqualTo(15);
    }


    @Test
    public void testGetAllStudents() {
        ResponseEntity<Student[]> response = restTemplate.getForEntity("/students", Student[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
    }
}
