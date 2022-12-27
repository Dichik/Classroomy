package com.main.classroomy.controlller;

import com.main.classroomy.entity.dto.TeacherDto;
import com.main.classroomy.service.TeacherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

    @RequestMapping(method = RequestMethod.GET)
    public List<TeacherDto> getAll() {
        return this.teacherService.getAll().stream()
                .map(teacher -> this.modelMapper.map(teacher, TeacherDto.class))
                .collect(Collectors.toList());
    }
// TODO encode teacher id - it is a bad practice to use id from DB
@RequestMapping(value = "/{id:[\\d+]}", method = RequestMethod.GET)
    public TeacherDto getById(@PathVariable Long id) {
        return this.modelMapper.map(this.teacherService.getById(id), TeacherDto.class);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TeacherDto> create(@Valid @RequestBody TeacherDto teacherDto) {
        return null;
    }

    @RequestMapping(value = "/{id:[\\d+]}", method = RequestMethod.DELETE)
    public ResponseEntity<TeacherDto> delete(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id:[\\d+]}", method = RequestMethod.PUT)
    public ResponseEntity<TeacherDto> update(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

}
