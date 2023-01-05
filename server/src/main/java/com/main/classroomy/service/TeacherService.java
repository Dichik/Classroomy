package com.main.classroomy.service;

import com.main.classroomy.entity.User;
import com.main.classroomy.entity.dto.UserDto;
import com.main.classroomy.exception.TeacherNotFoundException;
import com.main.classroomy.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TeacherService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    // FIXME
    public Optional<User> getById(Long id) {
        return this.userRepository.findById(id);
    }

    // FIXME
    public User create(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        return this.userRepository.save(user);
    }

    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }

    // FIXME
    public User updateById(Long id, UserDto userDto) {
        if (!this.userRepository.existsById(id)) {
            throw new TeacherNotFoundException(String.format("Teacher with id=%s was not found.", id));
        }
        User user = this.modelMapper.map(userDto, User.class);
        user.setId(id);
        return this.userRepository.save(user);
    }

}
