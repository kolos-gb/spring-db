package ru.hogwarts.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepositories;
import ru.hogwarts.school.repositories.StudentRepositories;
import ru.hogwarts.school.service.StudentService;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> repository = new HashMap<>();
    private final AvatarRepositories avatarRepository;
    private Long count = 0L;

    private final StudentRepositories studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepositories studentRepository, AvatarRepositories avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        if (!studentRepository.existsById(id)) {
            throw new NotFoundException("Student with id " + id + " not found");
        }
        student.setId(id);
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student with id " + id + " not found"));
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new NotFoundException("Student with id " + id + " not found");
        }
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> getStudentsByAgeRange(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public int getStudentCount() {
        return studentRepository.getStudentCount();
    }

    public double getAverageAge() {
        return studentRepository.getAverageAge();
    }

    public List<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }

}
