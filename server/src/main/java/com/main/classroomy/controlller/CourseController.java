package com.main.classroomy.controlller;

import com.main.classroomy.entity.Course;
import com.main.classroomy.entity.Post;
import com.main.classroomy.entity.dto.AssignmentDto;
import com.main.classroomy.entity.dto.CourseDto;
import com.main.classroomy.service.CourseService;
import com.main.classroomy.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Course> getAll() { // TODO add pageable
        return this.courseService.getAll();
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public CourseDto getById(@PathVariable Long id) {
        Course course = this.courseService.getById(id);
        if (course == null) {
            throw new RuntimeException(""); // FIXME exception
        }
        CourseDto courseDto = this.modelMapper.map(course, CourseDto.class);
        List<Post> posts = this.postService.getByCourseId(id);
        courseDto.setPosts(posts);
        return courseDto;
    }

    @RequestMapping(value = "/{id:\\d+}/deadlines", method = RequestMethod.GET)
    public List<AssignmentDto> getUrgentDeadlines(@PathVariable Long id) {
        // TODO research if we can get it from course_id
        // FIXME add caching mechanism
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Course create(@Valid @RequestBody CourseDto courseDto) {
        return this.courseService.create(courseDto);
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.PUT)
    public void updateById(@PathVariable Long id, @Valid @RequestBody CourseDto courseDto) {
        this.courseService.updateById(id, courseDto);
    }

    @RequestMapping(value = "{id:\\d+}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable Long id) {
        this.courseService.deleteById(id);
    }

}
