package com.main.classroomy.controlller;

import com.main.classroomy.entity.Course;
import com.main.classroomy.entity.Post;
import com.main.classroomy.entity.dto.CourseDto;
import com.main.classroomy.entity.dto.PostDto;
import com.main.classroomy.service.CourseService;
import com.main.classroomy.service.PostService;
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
    private final PostService postService;
    private final ModelMapper modelMapper;

    @Autowired
    public CourseController(CourseService courseService, PostService postService, ModelMapper modelMapper) {
        this.courseService = courseService;
        this.postService = postService;
        this.modelMapper = modelMapper;
    }

    //    @RolesAllowed({"USER", "ADMIN"})
    @RequestMapping(method = RequestMethod.GET)
    public List<Course> getAll() { // TODO add pageable
        return this.courseService.getAll();
    }

    //    @RolesAllowed({"USER", "ADMIN"})
    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public ResponseEntity<CourseDto> getById(@PathVariable Long id) {
        Course course = this.courseService.getById(id);
        if (course == null) {
            logger.warn("There is no course with id=" + id);
            throw new EntityNotFoundException(String.format("Course with id=[%s] was not found.", id));
        }
        CourseDto courseDto = this.modelMapper.map(course, CourseDto.class);
        List<Post> posts = this.postService.getByCourseId(id);
        courseDto.setPosts(posts);
        return ResponseEntity.ok(courseDto);
    }

    //    @RolesAllowed({"USER", "ADMIN"})
    @RequestMapping(value = "/{id:\\d+}/deadlines", method = RequestMethod.GET)
    public List<PostDto> getUrgentDeadlines(@PathVariable Long id) {
        return this.postService.getAssignmentsForNextWeek(id).stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }

    //    @RolesAllowed("ADMIN")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Course> create(@Valid @RequestBody CourseDto courseDto) {
        return ResponseEntity.ok(this.courseService.create(courseDto));
    }

    //    @RolesAllowed("ADMIN")
    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateById(@PathVariable Long id, @Valid @RequestBody CourseDto courseDto) {
        this.courseService.updateById(id, courseDto);
        return ResponseEntity.status(HttpStatus.OK).body("Course was successfully updated!");
    }

    //    @RolesAllowed("ADMIN")
    @RequestMapping(value = "{id:\\d+}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        this.courseService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("Course with id=[%s] was successfully deleted!", id));
    }

}
