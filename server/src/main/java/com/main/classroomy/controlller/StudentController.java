package com.main.classroomy.controlller;

import com.main.classroomy.entity.User;
import com.main.classroomy.entity.dto.UserDto;
import com.main.classroomy.service.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private static final Logger logger = LogManager.getLogger(StudentController.class);

    private final StudentService studentService;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentController(StudentService studentService, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAll() {
        List<User> students = this.studentService.getAll();
        if (students.isEmpty()) {
            logger.info("There are no students...");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
    @RequestMapping(value = "/{id:[\\d+]}", method = RequestMethod.GET)
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User student = this.studentService.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with id=" + id + " was not found!"));
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto userDto) {
        UserDto createdStudent = this.modelMapper.map(this.studentService.create(userDto), UserDto.class);
        if (createdStudent == null) {
            logger.warn("Something went wrong with creation of the student...");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/{id:[\\d+]}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        this.studentService.deleteById(id);
        return new ResponseEntity<>("Student was deleted successfully!", HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @RequestMapping(value = "/{id:[\\d+]}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        User student = this.studentService.updateById(id, userDto);
        return new ResponseEntity<>(this.modelMapper.map(student, UserDto.class), HttpStatus.OK);
    }

}
