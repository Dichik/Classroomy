package com.main.classroomy.controlller;

import com.main.classroomy.entity.Student;
import com.main.classroomy.entity.dto.StudentDto;
import com.main.classroomy.service.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
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

    @RolesAllowed("ADMIN")
    @RequestMapping(method = RequestMethod.GET)
    public List<Student> getAll() {
        return this.studentService.getAll();
    }

    @RolesAllowed({"USER", "ADMIN"})
    @RequestMapping(value = "/{id:[\\d+]}", method = RequestMethod.GET)
    public Student getById(@PathVariable Long id) {
        return this.studentService.getById(id);
    }

    @RolesAllowed("ADMIN")
    @RequestMapping(method = RequestMethod.POST)
    public void create(@Valid @RequestBody StudentDto studentDto) {
        this.studentService.create(studentDto);
    }

    @RolesAllowed({"USER", "ADMIN"})
    @RequestMapping(value = "/{id:[\\d+]}", method = RequestMethod.DELETE)
    public ResponseEntity<StudentDto> delete(@PathVariable Long id) {
//        this.studentService.delete(id);
        // TODO implement method
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed("USER")
    @RequestMapping(value = "/{id:[\\d+]}", method = RequestMethod.PUT)
    public ResponseEntity<StudentDto> update(@PathVariable Long id, @Valid @RequestBody StudentDto studentDto) {
        this.studentService.updateById(id, studentDto);
        // FIXME user can't update email field
        return ResponseEntity.noContent().build();
    }

}
