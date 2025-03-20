package ru.hogwarts.school.repositories;

import ru.hogwarts.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepositories extends JpaRepository<Student, Long> {

}
