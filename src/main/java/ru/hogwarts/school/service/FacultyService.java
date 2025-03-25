package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

@Service
public interface FacultyService {
    Faculty addFaculty(Faculty faculty);

    Faculty updateFaculty(Long id, Faculty faculty);

    Faculty getFaculty(Long id);

    void deleteFaculty(Long id);

    List<Faculty> getAll();

    List<Faculty> findFacultiesByNameOrColor(String query);

}
