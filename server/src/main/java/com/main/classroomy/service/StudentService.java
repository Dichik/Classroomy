package com.main.classroomy.service;

import com.main.classroomy.entity.User;
import com.main.classroomy.entity.dto.UserDto;
import com.main.classroomy.exception.StudentNotFoundException;
import com.main.classroomy.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private static final Logger logger = LogManager.getLogger(StudentService.class);

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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

}
