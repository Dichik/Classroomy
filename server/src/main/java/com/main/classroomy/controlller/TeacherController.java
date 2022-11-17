package com.main.classroomy.controlller;

import com.main.classroomy.entity.dto.TeacherDto;
import com.main.classroomy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<TeacherDto> getAll() {
        return List.of();
    }

    @GetMapping("/{id:[\\d+]}")
    public ResponseEntity<TeacherDto> getById(@PathVariable Long id) {
        return null;
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
