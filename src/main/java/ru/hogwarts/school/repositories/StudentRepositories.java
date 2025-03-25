package ru.hogwarts.school.repositories;

import ru.hogwarts.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepositories extends JpaRepository<Student, Long> {
    List<Student> findByAgeBetween(int min, int max);
}
