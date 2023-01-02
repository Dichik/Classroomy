package com.main.classroomy.controlller;

import com.main.classroomy.entity.Course;
import com.main.classroomy.entity.dto.CourseDto;
import com.main.classroomy.service.CourseService;
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
@RequestMapping("/courses")
public class CourseController {
    private static final Logger logger = LogManager.getLogger(CourseController.class);

    private final CourseService courseService;
    private final ModelMapper modelMapper;

    @Autowired
    public CourseController(CourseService courseService, ModelMapper modelMapper) {
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Course>> getAll() { // TODO add pageable
        List<Course> courses = this.courseService.getAll();
        if (courses.isEmpty()) {
            logger.info("List of courses is empty!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public ResponseEntity<CourseDto> getById(@PathVariable Long id) {
        Course course = this.courseService.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course with id=" + id + " was not found!"));

        CourseDto courseDto = this.modelMapper.map(course, CourseDto.class);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Course> create(@Valid @RequestBody CourseDto courseDto) {
        return new ResponseEntity<>(this.courseService.create(courseDto), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateById(@PathVariable Long id, @Valid @RequestBody CourseDto courseDto) {
        this.courseService.updateById(id, courseDto);
        return new ResponseEntity<>("Course was successfully updated!", HttpStatus.OK);
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        this.courseService.deleteById(id);

        String message = String.format("Course with id=[%s] was successfully deleted!", id);
        return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
    }

}
