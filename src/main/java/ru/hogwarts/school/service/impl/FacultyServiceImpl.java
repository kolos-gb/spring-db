package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepositories;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;

@Service
public class FacultyServiceImpl implements FacultyService {
    private static final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private final Map<Long, Faculty> repository = new HashMap<>();
    private Long count = 0L;

    private final FacultyRepositories facultyRepository;

    @Autowired
    public FacultyServiceImpl(FacultyRepositories facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for add faculty");
        faculty.setId(++count);
        repository.put(count, faculty);
        logger.debug("Faculty added with id = {}", faculty.getId());
        return faculty;
    }

    @Override
    public Faculty updateFaculty(Long id, Faculty faculty) {
        logger.info("Was invoked method for update faculty with id = {}", id);
        if (!repository.containsKey(id)) {
            logger.error("Update failed, faculty with id {} not found", id);
            throw new NotFoundException("Not found id - " + id);
        }
        faculty.setId(id);
        repository.put(id, faculty);
        return faculty;
    }

    @Override
    public Faculty getFaculty(Long id) {
        logger.info("Was invoked method for get faculty with id = {}", id);
        Faculty faculty = repository.get(id);
        if (faculty == null) {
            logger.warn("No faculty found with id = {}", id);
        }
        return repository.get(id);
    }

    @Override
    public void deleteFaculty(Long id) {
        logger.info("Was invoked method for delete faculty with id = {}", id);
        if (!repository.containsKey(id)) {
            logger.error("Delete failed, faculty with id {} not found", id);
            throw new NotFoundException("Not found id - " + id);
        }
        repository.remove(id);
    }

    @Override
    public List<Faculty> getAll() {
        logger.info("Was invoked method for get all faculties");
        return Collections.unmodifiableList(new ArrayList<>(repository.values()));
    }

    @Override
    public List<Faculty> findFacultiesByNameOrColor(String query) {
        logger.info("Was invoked method for find faculties by name or color with query = {}", query);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(query, query);
    }

    public String getLongestFacultyName() {
        List<Faculty> faculties = facultyRepository.findAll();
        return faculties.stream()
                .map(Faculty::getName)
                .max((name1, name2) -> Integer.compare(name1.length(), name2.length()))
                .orElse("No faculties found");
    }


}
