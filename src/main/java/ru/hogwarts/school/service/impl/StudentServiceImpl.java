package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
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
        logger.info("Was invoked method for add student");
        student.setId(++count);
        repository.put(count, student);
        logger.debug("Student added with id = {}", student.getId());
        return student;
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        logger.info("Was invoked method for update student with id = {}", id);
        if (!repository.containsKey(id)) {
            logger.error("Update failed, student with id {} not found", id);
            throw new NotFoundException("Not found id - " + id);
        }
        student.setId(id);
        repository.put(id, student);
        return student;
    }

    @Override
    public Student getStudent(Long id) {
        logger.info("Was invoked method for get student with id = {}", id);
        Student student = repository.get(id);
        if (student == null) {
            logger.warn("No student found with id = {}", id);
        }
        return repository.get(id);
    }

    @Override
    public void deleteStudent(Long id) {
        logger.info("Was invoked method for delete student with id = {}", id);
        if (!repository.containsKey(id)) {
            logger.error("Delete failed, student with id {} not found", id);
            throw new NotFoundException("Not found id - " + id);
        }
        repository.remove(id);
    }

    @Override
    public List<Student> getAll() {
        logger.info("Was invoked method for get all students");
        return Collections.unmodifiableList(new ArrayList<>(repository.values()));
    }

    @Override
    public List<Student> getStudentsByAgeRange(int min, int max) {
        logger.info("Was invoked method for get students by age range: {} - {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    public int getStudentCount() {
        logger.info("Was invoked method for get student count");
        return studentRepository.getStudentCount();
    }

    public double getAverageAge() {
        logger.info("Was invoked method for get average student age");
        return studentRepository.getAverageAge();
    }

    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method for get last five students");
        return studentRepository.getLastFiveStudents();
    }

}
