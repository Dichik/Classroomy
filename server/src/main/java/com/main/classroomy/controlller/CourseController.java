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

    private final Long URGENT_PARAM = 7L;

    @Autowired
    public CourseController(CourseService courseService, PostService postService, ModelMapper modelMapper) {
        this.courseService = courseService;
        this.postService = postService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<Course> getAll() { // TODO add pageable
        return this.courseService.getAll();
    }

    @GetMapping("/{id:\\d+}")
    public CourseDto getById(@PathVariable Long id) {
        Course course = this.courseService.getById(id);
        if (course == null) {
            throw new RuntimeException("");
        }
        CourseDto courseDto = this.modelMapper.map(course, CourseDto.class);
        List<Post> posts = this.postService.getByCourseId(id);
        courseDto.setPosts(posts);
        return courseDto;
    }

    @GetMapping("/{id:\\d+}")
    public List<AssignmentDto> getUrgentDeadlines(@PathVariable Long id) {
        // TODO research if we can get it from course_id
        // FIXME add caching mechanism
        return null;
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
