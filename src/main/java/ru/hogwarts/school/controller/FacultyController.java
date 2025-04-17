package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exception.NotFoundException;
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
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PutMapping("/{id}")
    public Faculty updateFaculty(@PathVariable(name = "id") Long id, @RequestBody Faculty faculty) {
        return facultyService.updateFaculty(id, faculty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable(name = "id") Long id) {
        try {
            Faculty faculty = facultyService.getFaculty(id);
            return ResponseEntity.ok(faculty);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
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
