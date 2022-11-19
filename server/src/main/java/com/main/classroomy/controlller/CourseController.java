package com.main.classroomy.controlller;

import com.main.classroomy.entity.Course;
import com.main.classroomy.entity.dto.CourseDto;
import com.main.classroomy.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAll() { // TODO add pageable
        return this.courseService.getAll();
    }

    @GetMapping("/{id:\\d+}")
    public Course getById(@PathVariable Long id) {
        return this.courseService.getById(id);
    }

    @PostMapping
    public Course create(@Valid CourseDto courseDto) {
        return this.courseService.create(courseDto);
    }

    @PutMapping("/{id:\\d+}")
    public void updateById(@PathVariable Long id, @Valid @RequestBody CourseDto courseDto) {
        this.courseService.updateById(id, courseDto);
    }

    @DeleteMapping("{id:\\d+}")
    public void deleteById(@PathVariable Long id) {
        this.courseService.deleteById(id);
    }

}
