package com.main.classroomy.service;

import com.main.classroomy.entity.CourseUser;
import com.main.classroomy.entity.User;
import com.main.classroomy.entity.dto.UserDto;
import com.main.classroomy.exception.StudentNotFoundException;
import com.main.classroomy.repository.CourseUserRepository;
import com.main.classroomy.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CourseUserRepository courseUserRepository;

    @Autowired
    public StudentService(UserRepository userRepository, ModelMapper modelMapper, CourseUserRepository courseUserRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.courseUserRepository = courseUserRepository;
    }

    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    public Optional<User> getById(Long id) {
        return this.userRepository.findById(id);
    }

    public Optional<User> getByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User create(UserDto userDto) {
        return this.userRepository.save(this.modelMapper.map(userDto, User.class));
    }

    public User updateById(Long id, UserDto userDto) {
        if (!this.userRepository.existsById(id)) {
            throw new StudentNotFoundException(String.format("Student with id=%s was not found!", id));
        }
        User user = this.modelMapper.map(userDto, User.class);
        user.setId(id);
        return this.userRepository.save(user);
    }

    public void deleteById(Long id) {
        if (!this.userRepository.existsById(id)) {
            throw new StudentNotFoundException(String.format("Student with id=%s was not found!", id));
        }
        this.userRepository.deleteById(id);
    }

    public List<User> getAllEnrolledInCourse(Long courseId) {
        List<String> usernames = this.courseUserRepository.findAllByCourseId(courseId).stream()
                .map(CourseUser::getUsername).toList();
        return usernames.stream().map(this.userRepository::findByUsername
                ).filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toList());
    }
}
