package ru.hogwarts.school.repositories;

import ru.hogwarts.school.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepositories extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColorIgnoreCase(String color);
    List<Faculty> findByNameIgnoreCase(String name);
    List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
}
