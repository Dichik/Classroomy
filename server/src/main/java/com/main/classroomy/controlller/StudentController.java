package com.main.classroomy.controlller;

import com.main.classroomy.entity.Student;
import com.main.classroomy.entity.dto.StudentDto;
import com.main.classroomy.service.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private static final Logger logger = LogManager.getLogger(StudentController.class);

    private final StudentService studentService;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentController(StudentService studentService, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Student>> getAll() {
        List<Student> students = this.studentService.getAll();
        if (students.isEmpty()) {
            logger.info("There are no students...");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id:[\\d+]}", method = RequestMethod.GET)
    public ResponseEntity<Student> getById(@PathVariable Long id) {
        Student student = this.studentService.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with id=" + id + " was not found!"));
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<StudentDto> create(@Valid @RequestBody StudentDto studentDto) {
        StudentDto createdStudent = this.modelMapper.map(this.studentService.create(studentDto), StudentDto.class);
        if (createdStudent == null) {
            logger.warn("Something went wrong with creation of the student...");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id:[\\d+]}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        this.studentService.deleteById(id);
        return new ResponseEntity<>("Student was deleted successfully!", HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id:[\\d+]}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody StudentDto studentDto) {
        Student student = this.studentService.updateById(id, studentDto);
        return new ResponseEntity<>(this.modelMapper.map(student, StudentDto.class), HttpStatus.OK);
    }

}
