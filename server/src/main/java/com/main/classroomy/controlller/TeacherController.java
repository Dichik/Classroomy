package com.main.classroomy.controlller;

import com.main.classroomy.entity.Teacher;
import com.main.classroomy.entity.dto.TeacherDto;
import com.main.classroomy.exception.TeacherNotFoundException;
import com.main.classroomy.service.TeacherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private static final Logger logger = LogManager.getLogger(TeacherController.class);

    private final TeacherService teacherService;
    private final ModelMapper modelMapper;

    @Autowired
    public TeacherController(TeacherService teacherService, ModelMapper modelMapper) {
        this.teacherService = teacherService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/{id:[\\d+]}", method = RequestMethod.GET)
    public ResponseEntity<TeacherDto> getById(@PathVariable Long id) {
        Teacher teacher = this.teacherService.getById(id)
                .orElseThrow(() -> new TeacherNotFoundException(String.format("Teacher with id=%s was not found.", id)));
        return new ResponseEntity<>(this.modelMapper.map(teacher, TeacherDto.class), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TeacherDto> create(@Valid @RequestBody TeacherDto teacherDto) {
        Teacher teacher = this.teacherService.create(teacherDto);
        if (teacher == null) {
            logger.warn("Something went wrong, couldn't create teacher...");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(this.modelMapper.map(teacher, TeacherDto.class), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id:[\\d+]}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        this.teacherService.deleteById(id);
        return new ResponseEntity<>("Teacher was successfully deleted!", HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id:[\\d+]}", method = RequestMethod.PUT)
    public ResponseEntity<TeacherDto> update(@PathVariable Long id, @Valid @RequestBody TeacherDto teacherDto) {
        Teacher teacher = this.teacherService.updateById(id, teacherDto);
        return new ResponseEntity<>(this.modelMapper.map(teacher, TeacherDto.class), HttpStatus.OK);
    }

}
