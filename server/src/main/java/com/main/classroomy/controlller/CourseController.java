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

    @RequestMapping(value = "/{id:\\d+}/deadlines", method = RequestMethod.GET)
    public ResponseEntity<List<PostDto>> getUrgentDeadlines(@PathVariable Long id) {
        List<PostDto> posts = this.postService.getAssignmentsForNextWeek(id).stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
        if (posts.isEmpty()) {
            logger.info("List of posts is empty!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

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

    @RequestMapping(value = "/{id:\\d+}/posts", method = RequestMethod.GET)
    public ResponseEntity<List<Post>> getPostsByCourseId(@PathVariable Long id) {
        List<Post> posts = this.postService.getByCourseId(id);
        if (posts.isEmpty()) {
            logger.info("No posts found for course with id=" + id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

}
