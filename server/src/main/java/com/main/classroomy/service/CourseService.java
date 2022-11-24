package com.main.classroomy.service;

import com.main.classroomy.entity.Course;
import com.main.classroomy.entity.dto.CourseDto;
import com.main.classroomy.exception.CourseNotFoundException;
import com.main.classroomy.repository.CourseRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private static final Logger logger = LogManager.getLogger(CourseService.class);

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CourseService(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    public List<Course> getAll() {
        return this.courseRepository.findAll();
    }

    public Course getById(Long id) {
        return this.courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course with id=" + id + " was not found!"));
    }

    public Course create(CourseDto courseDto) {
        return this.courseRepository.save(
                this.modelMapper.map(courseDto, Course.class)
        );
    }

    public void updateById(Long id, CourseDto courseDto) {
        if (!this.courseRepository.existsById(id)) {
            throw new CourseNotFoundException("Course with id=" + id + " was not found!");
        }
        Course course = this.modelMapper.map(courseDto, Course.class);
        course.setId(id);
        this.courseRepository.save(course);
    }

    public void deleteById(Long id) {
        this.courseRepository.deleteById(id);
    }

}
