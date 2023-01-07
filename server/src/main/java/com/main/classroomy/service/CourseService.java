package com.main.classroomy.service;

import com.main.classroomy.entity.Course;
import com.main.classroomy.entity.CourseUser;
import com.main.classroomy.entity.User;
import com.main.classroomy.entity.dto.CourseDto;
import com.main.classroomy.exception.CourseNotFoundException;
import com.main.classroomy.repository.CourseRepository;
import com.main.classroomy.repository.CourseUserRepository;
import com.main.classroomy.repository.UserRepository;
import com.main.classroomy.security.service.UserDetailsImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseUserRepository courseUserRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CourseService(CourseRepository courseRepository, CourseUserRepository courseUserRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.courseUserRepository = courseUserRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<Course> getAll() {
        return this.courseRepository.findAll();
    }

    public Optional<Course> getById(Long id) {
        return this.courseRepository.findById(id);
    }

    public Course create(CourseDto courseDto, UserDetailsImpl userDetails) {
        String enrollmentKey = generateEnrollmentKey();
        courseDto.setEnrollmentKey(enrollmentKey);

        Course course = this.modelMapper.map(courseDto, Course.class);
        course.setCreatedByUsername(userDetails.getUsername());

        return this.courseRepository.save(course);
    }

    private String generateEnrollmentKey() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; ++ i) {
            sb.append(random.nextInt(100));
        }
        return sb.toString();
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

    public List<Course> getAllByUsername(String username) {
        List<Long> ids = this.courseUserRepository.findAllByUsername(username).stream()
                .map(CourseUser::getCourseId).toList();
        return this.courseRepository.findAllById(ids);
    }

    public void enroll(String enrollmentKey, UserDetailsImpl userDetails) {
        Course course = this.courseRepository.findByEnrollmentKey(enrollmentKey).orElse(null);
        if (course == null) {
            throw new EntityNotFoundException("Course was not found.");
        }
        String username = userDetails.getUsername();
        CourseUser courseUser = CourseUser.builder()
                .username(username)
                .courseId(course.getId())
                .build();
        this.courseUserRepository.save(courseUser);
    }

    public List<Course> getAllByCreatedByUsername(String username) {
        return this.courseRepository.findAllByCreatedByUsername(username);
    }
}
