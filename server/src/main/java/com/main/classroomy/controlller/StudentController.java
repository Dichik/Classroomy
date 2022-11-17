package com.main.classroomy.controlller;

import com.main.classroomy.entity.dto.StudentDto;
import com.main.classroomy.entity.Student;
import com.main.classroomy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAll() {
        return List.of();
    }

    @GetMapping("/{id:[\\d+]}")
    public ResponseEntity<Student> getById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<StudentDto> create(@Valid @RequestBody StudentDto studentDto) {
        return null;
    }

    @DeleteMapping("/{id:[\\d+]}")
    public ResponseEntity<StudentDto> delete(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id:[\\d+]}")
    public ResponseEntity<StudentDto> update(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

}
