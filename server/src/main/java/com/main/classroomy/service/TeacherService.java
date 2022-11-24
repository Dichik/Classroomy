package com.main.classroomy.service;

import com.main.classroomy.entity.dto.TeacherDto;
import com.main.classroomy.entity.Teacher;
import com.main.classroomy.exception.TeacherNotFoundException;
import com.main.classroomy.repository.TeacherRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private static final Logger logger = LogManager.getLogger(TeacherService.class);

    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository, ModelMapper modelMapper) {
        this.teacherRepository = teacherRepository;
        this.modelMapper = modelMapper;
    }

    public List<Teacher> getAll() {
        return this.teacherRepository.findAll();
    }

    public Teacher getById(Long id) {
        return this.teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher with id=" + id + " was not found."));
    }

    public Teacher create(TeacherDto teacherDto) {
        return this.teacherRepository.save(
                this.modelMapper.map(teacherDto, Teacher.class)
        );
    }
// TODO if we are doing to use email as key than we should disallow password change
    public Teacher update(TeacherDto teacherDto) {
        return null;
    }

    public void deleteById(Long id) {
        this.teacherRepository.deleteById(id);
    }

}
