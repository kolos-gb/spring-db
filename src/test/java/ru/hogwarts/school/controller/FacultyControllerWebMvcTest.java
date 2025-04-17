package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FacultyController.class)
class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FacultyService facultyService;

    @Test
    public void testGetFacultyById() throws Exception {
        List<Student> students = Arrays.asList();
        Faculty faculty = new Faculty(1L, "Faculty of Magic", "Red", students);
        when(facultyService.getFaculty(1L)).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculties/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Faculty of Magic"))
                .andExpect(jsonPath("$.color").value("Red"));
    }

    @Test
    public void testGetFacultyById_NotFound() throws Exception {
        when(facultyService.getFaculty(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculties/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddFaculty() throws Exception {
        List<Student> students = Arrays.asList();
        Faculty faculty = new Faculty(1L, "Faculty of Magic", "Red", students);
        when(facultyService.addFaculty(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.post("/faculties")
                        .contentType("application/json")
                        .content("{\"name\":\"Faculty of Magic\", \"color\":\"Red\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Faculty of Magic"))
                .andExpect(jsonPath("$.color").value("Red"));
    }

    @Test
    public void testUpdateFaculty() throws Exception {
        List<Student> students = Arrays.asList();
        Faculty faculty = new Faculty(1L, "Faculty of Magic", "Red", students);
        when(facultyService.updateFaculty(eq(1L), any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.put("/faculties/1")
                        .contentType("application/json")
                        .content("{\"name\":\"Faculty of Magic\", \"color\":\"Red\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Faculty of Magic"))
                .andExpect(jsonPath("$.color").value("Red"));
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/faculties/1"))
                .andExpect(status().isOk());

        verify(facultyService, times(1)).deleteFaculty(1L);
    }

    @Test
    public void testGetAllFaculties() throws Exception {
        List<Student> students = Arrays.asList();
        Faculty faculty1 = new Faculty(1L, "Faculty of Magic", "Red", students);
        Faculty faculty2 = new Faculty(2L, "Faculty of Science", "Blue", students);
        List<Faculty> faculties = Arrays.asList(faculty1, faculty2);

        when(facultyService.getAll()).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Faculty of Magic"))
                .andExpect(jsonPath("$[1].name").value("Faculty of Science"));
    }

    @Test
    public void testSearchFaculties() throws Exception {
        List<Student> students = Arrays.asList();
        Faculty faculty = new Faculty(1L, "Faculty of Magic", "Red", students);
        List<Faculty> faculties = Arrays.asList(faculty);

        when(facultyService.findFacultiesByNameOrColor("Magic")).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculties/search")
                        .param("query", "Magic"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Faculty of Magic"));
    }

    @Test
    public void testGetStudentsByFaculty() throws Exception {
        List<Student> students = Arrays.asList();
        Faculty faculty = new Faculty(1L, "Faculty of Magic", "Red", students);
        when(facultyService.getFaculty(1L)).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculties/1/students"))
                .andExpect(status().isOk());
    }
}
