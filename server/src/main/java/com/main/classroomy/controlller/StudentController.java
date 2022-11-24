package com.main.classroomy.controlller;

import com.main.classroomy.entity.dto.StudentDto;
import com.main.classroomy.entity.Student;
import com.main.classroomy.service.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private static final Logger logger = LogManager.getLogger(StudentController.class);

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAll() {
        return this.studentService.getAll();
    }

    @GetMapping("/{id:[\\d+]}")
    public Student getById(@PathVariable Long id) {
        return this.studentService.getById(id);
    }

    @PostMapping
    public void create(@Valid @RequestBody StudentDto studentDto) {
        this.studentService.create(studentDto);
    }

    @DeleteMapping("/{id:[\\d+]}")
    public ResponseEntity<StudentDto> delete(@PathVariable Long id) {
//        this.studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id:[\\d+]}")
    public ResponseEntity<StudentDto> update(@PathVariable Long id) {
//        this.studentService.updateById(id);
        return ResponseEntity.noContent().build();
    }

}
