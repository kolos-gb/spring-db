package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.net.URI;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTestRestTemplateTest {

    @LocalServerPort
    private int port;

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
        Faculty faculty = new Faculty(null, "Gryffindor", "Red", Collections.emptyList());
        Faculty created = restTemplate.postForEntity("/faculties", faculty, Faculty.class).getBody();
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculties/" + created.getId(), Faculty.class);
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

    @Test
    public void testUpdateFaculty() {
        Faculty faculty = new Faculty(null, "Ravenclaw", "Blue", Collections.emptyList());
        Faculty created = restTemplate.postForEntity("/faculties", faculty, Faculty.class).getBody();

        Faculty updatedFaculty = new Faculty(created.getId(), "Ravenclaw", "Dark Blue", Collections.emptyList());
        HttpEntity<Faculty> requestUpdate = new HttpEntity<>(updatedFaculty);
        ResponseEntity<Faculty> response = restTemplate.exchange("/faculties/" + created.getId(), HttpMethod.PUT, requestUpdate, Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getColor()).isEqualTo("Dark Blue");
    }

    @Test
    public void testDeleteFaculty() {
        Faculty faculty = new Faculty(null, "Durmstrang", "Black", Collections.emptyList());
        URI location = restTemplate.postForLocation(createUrl("/faculties"), faculty);
        if (location == null) {
            fail("Location is null after saving the faculty");
        }
        long facultyId = extractIdFromUri(location);
        restTemplate.delete(createUrl("/faculties/" + facultyId));
        ResponseEntity<Faculty> response = restTemplate.getForEntity(createUrl("/faculties/" + facultyId),
                Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetStudentsByFaculty() {
        Faculty faculty = new Faculty(null, "TestFacultyWithStudents", "Grey", Collections.emptyList());
        Faculty created = restTemplate.postForEntity("/faculties", faculty, Faculty.class).getBody();
        ResponseEntity<Student[]> response = restTemplate.getForEntity("/faculties/" + created.getId() + "/students", Student[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    private URI createUrl(String path) {
        return URI.create("http://localhost:" + port + path);
    }

    private long extractIdFromUri(URI uri) {
        if (uri == null) {
            fail("URI is null");
        }
        String path = uri.getPath();
        return Long.parseLong(path.substring(path.lastIndexOf('/') + 1));
    }
}
