package ru.hogwarts.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepositories;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> repository = new HashMap<>();
    private Long count = 0L;

    private final FacultyRepositories facultyRepository;

    @Autowired
    public FacultyServiceImpl(FacultyRepositories facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(++count);
        repository.put(count, faculty);
        return faculty;
    }

    @Override
    public Faculty updateFaculty(Long id, Faculty faculty) {
        if (!repository.containsKey(id)) {
            throw new NotFoundException("Not found id - " + id);
        }
        faculty.setId(id);
        repository.put(id, faculty);
        return faculty;
    }

    @Override
    public Faculty getFaculty(Long id) {
        Faculty faculty = repository.get(id);
        if (faculty == null) {
            throw new NotFoundException("Faculty not found with id: " + id);
        }
        return faculty;
    }

    @Override
    public void deleteFaculty(Long id) {
        if (!repository.containsKey(id)) {
            throw new NotFoundException("Faculty not found with id: " + id);
        }
        repository.remove(id);
    }

    @Override
    public List<Faculty> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(repository.values()));
    }

    @Override
    public List<Faculty> findFacultiesByNameOrColor(String query) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(query, query);
    }


}
