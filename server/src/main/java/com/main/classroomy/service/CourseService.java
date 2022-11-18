package com.main.classroomy.service;

import com.main.classroomy.entity.Course;
import com.main.classroomy.entity.dto.CourseDto;
import com.main.classroomy.repository.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

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

    public Course create(CourseDto courseDto) {
        return this.courseRepository.save(
                this.modelMapper.map(courseDto, Course.class)
        );
    }

    public void updateById(Long id, CourseDto courseDto) {
        // TODO implement update method
    }

}
