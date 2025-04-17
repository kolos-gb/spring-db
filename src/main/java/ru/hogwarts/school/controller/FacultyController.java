package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @PostMapping
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyService.addFaculty(faculty);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFaculty);
    }


    @PutMapping("/{id}")
    public Faculty updateFaculty(@PathVariable(name = "id") Long id, @RequestBody Faculty faculty) {
        return facultyService.updateFaculty(id, faculty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable(name = "id") Long id) {
        Faculty faculty = facultyService.getFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();  // Возвращает статус 404 Not Found
        }
        return ResponseEntity.ok(faculty);  // Возвращает статус 200 OK с объектом Faculty
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable(name = "id") Long id) {
        facultyService.deleteFaculty(id);
    }

    @GetMapping
    public List<Faculty> getAll() {
        return facultyService.getAll();
    }

    @GetMapping("/search")
    public List<Faculty> searchFaculties(@RequestParam String query) {
        return facultyService.findFacultiesByNameOrColor(query);
    }

    @GetMapping("/{id}/students")
    public List<Student> getStudentsByFaculty(@PathVariable Long id) {
        return facultyService.getFaculty(id).getStudents();
    }


}
