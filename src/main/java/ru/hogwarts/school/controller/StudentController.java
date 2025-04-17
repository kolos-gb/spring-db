package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student createStudent = studentService.addStudent(student);
        URI location = URI.create("/students/" + createStudent.getId());  // Создаём URI для нового студента
        return ResponseEntity.created(location).body(createStudent);  // Используем созданный URI в ответе
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable(name = "id") Long id, @RequestBody Student student) {
        student.setId(id);
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable(name = "id") Long id) {
        try {
            studentService.deleteStudent(id); // Пытаемся удалить студента
            return ResponseEntity.noContent().build(); // Студент успешно удален, возвращаем статус 204
        } catch (NotFoundException e) {
            System.out.println("Student not found with id: " + id); // Логирование
            return ResponseEntity.notFound().build(); // Если студент не найден, возвращаем 404
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable(name = "id") Long id) {
        try {
            Student student = studentService.getStudent(id);
            return ResponseEntity.ok(student);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Student> getAll() {
        return studentService.getAll();
    }

    @GetMapping("/by-age")
    public List<Student> getStudentsByAgeRange(@RequestParam int min, @RequestParam int max) {
        return studentService.getStudentsByAgeRange(min, max);
    }

    @GetMapping("/{id}/faculty")
    public Faculty getFacultyByStudent(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        if (student != null && student.getFaculty() != null) {
            System.out.println("Faculty found for student: " + student.getFaculty().getName());
            return student.getFaculty();
        } else {
            System.out.println("No faculty found for student with id: " + id);
            throw new NotFoundException("Faculty not found for student with id: " + id);
        }
    }

}