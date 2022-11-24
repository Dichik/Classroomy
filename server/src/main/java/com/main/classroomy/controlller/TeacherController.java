package com.main.classroomy.controlller;

import com.main.classroomy.entity.Teacher;
import com.main.classroomy.entity.dto.TeacherDto;
import com.main.classroomy.service.TeacherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    @GetMapping
    public List<TeacherDto> getAll() {
        return this.teacherService.getAll().stream()
                .map(teacher -> this.modelMapper.map(teacher, TeacherDto.class))
                .collect(Collectors.toList());
    }
// TODO encode teacher id - it is a bad practice to use id from DB
    @GetMapping("/{id:[\\d+]}")
    public TeacherDto getById(@PathVariable Long id) {
        return this.modelMapper.map(this.teacherService.getById(id), TeacherDto.class);
    }

    @PostMapping
    public ResponseEntity<TeacherDto> create(@Valid @RequestBody TeacherDto teacherDto) {
        return null;
    }

    @DeleteMapping("/{id:[\\d+]}")
    public ResponseEntity<TeacherDto> delete(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id:[\\d+]}")
    public ResponseEntity<TeacherDto> update(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

}
