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
import java.util.stream.Collectors;

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

    public List<String> getNamesStartingWithA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name != null && name.toUpperCase().startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public synchronized void printStudentName(Student student) {
        System.out.println(student.getName());
    }

    @Override
    public void printStudentsParallel() {
        List<Student> students = getAllStudents().stream().limit(6).toList();
        if (students.size() < 6) {
            System.out.println("Недостаточно студентов для параллельной печати.");
            return;
        }

        System.out.println("Печать в параллельном режиме:");

        System.out.println(students.get(0).getName());
        System.out.println(students.get(1).getName());

        Thread thread1 = new Thread(() -> {
            System.out.println(students.get(2).getName());
            System.out.println(students.get(3).getName());
        });

        Thread thread2 = new Thread(() -> {
            System.out.println(students.get(4).getName());
            System.out.println(students.get(5).getName());
        });

        thread1.start();
        thread2.start();
    }

    @Override
    public void printStudentsSynchronized() {
        List<Student> students = getAllStudents().stream().limit(6).toList();
        if (students.size() < 6) {
            System.out.println("Недостаточно студентов для синхронизированной печати.");
            return;
        }

        System.out.println("Печать в синхронизированном режиме:");

        printStudentName(students.get(0));
        printStudentName(students.get(1));

        Thread thread1 = new Thread(() -> {
            printStudentName(students.get(2));
            printStudentName(students.get(3));
        });

        Thread thread2 = new Thread(() -> {
            printStudentName(students.get(4));
            printStudentName(students.get(5));
        });

        thread1.start();
        thread2.start();
    }

}
