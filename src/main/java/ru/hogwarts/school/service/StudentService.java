package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.List;

@Service
public interface StudentService {
    Student addStudent(Student student);

    Student updateStudent(Long id, Student student);

    Student getStudent(Long id);

    void deleteStudent(Long id);

    List<Student> getAll();

    List<Student> getStudentsByAgeRange(int min, int max);

    List<Student> getLastFiveStudents();

    double getAverageAge();

    int getStudentCount();
}
