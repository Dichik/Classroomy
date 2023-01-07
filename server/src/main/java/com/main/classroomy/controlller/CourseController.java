package com.main.classroomy.controlller;

import com.main.classroomy.entity.Course;
import com.main.classroomy.entity.dto.CourseDto;
import com.main.classroomy.security.service.UserDetailsImpl;
import com.main.classroomy.service.CourseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private static final Logger logger = LogManager.getLogger(CourseController.class);

    private final CourseService courseService;
    private final ModelMapper modelMapper;

    @Autowired
    public CourseController(CourseService courseService, ModelMapper modelMapper) {
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Course>> getAll(@AuthenticationPrincipal UserDetailsImpl userDetails) { // TODO add pageable
        String username = userDetails.getUsername();
        List<Course> courses;
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        if (roles.contains("ROLE_TEACHER")) {
            courses = this.courseService.getAllByCreatedByUsername(username);
        } else {
            courses = this.courseService.getAllByUsername(username);
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
    @RequestMapping(value = "/enroll", method = RequestMethod.POST)
    public ResponseEntity<?> enroll(@RequestBody CourseDto courseDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        this.courseService.enroll(courseDto.getEnrollmentKey(), userDetails);
        return ResponseEntity.ok().body("enrolled successfully.");
    }

    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public ResponseEntity<CourseDto> getById(@PathVariable Long id) {
        Course course = this.courseService.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course with id=" + id + " was not found!"));

        CourseDto courseDto = this.modelMapper.map(course, CourseDto.class);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Course> create(@Valid @RequestBody CourseDto courseDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<>(this.courseService.create(courseDto, userDetails), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateById(@PathVariable Long id, @Valid @RequestBody CourseDto courseDto) {
        this.courseService.updateById(id, courseDto);
        return new ResponseEntity<>("Course was successfully updated!", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        this.courseService.deleteById(id);

        String message = String.format("Course with id=[%s] was successfully deleted!", id);
        return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
    }

}
