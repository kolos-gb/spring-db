package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTestRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAddFaculty() {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red", Collections.emptyList());
        ResponseEntity<Faculty> response = restTemplate.postForEntity("/faculties", faculty, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Gryffindor");
        assertThat(response.getBody().getColor()).isEqualTo("Red");
    }

    @Test
    public void testGetFaculty() {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red", Collections.emptyList());
        restTemplate.postForEntity("/faculties", faculty, Faculty.class);
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculties/1", Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Gryffindor");
        assertThat(response.getBody().getColor()).isEqualTo("Red");
    }

    @Test
    public void testGetAllFaculties() {
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity("/faculties", Faculty[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
    }
}
