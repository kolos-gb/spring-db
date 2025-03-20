package ru.hogwarts.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepositories;
import ru.hogwarts.school.repositories.StudentRepositories;
import ru.hogwarts.school.service.StudentService;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> repository = new HashMap<>();
    private Long count = 0L;

    private final StudentRepositories studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepositories studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        student.setId(++count);
        repository.put(count, student);
        return student;
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        if (!repository.containsKey(id)) {
            throw new NotFoundException("Not found id - " + id);
        }
        student.setId(id);
        repository.put(id, student);
        return student;
    }

    @Override
    public Student getStudent(Long id) {
        return repository.get(id);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!repository.containsKey(id)) {
            throw new NotFoundException("Not found id - " + id);
        }
        repository.remove(id);
    }

    @Override
    public List<Student> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(repository.values()));
    }
}
