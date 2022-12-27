package com.main.classroomy.service;

import com.main.classroomy.entity.dto.TeacherDto;
import com.main.classroomy.entity.Teacher;
import com.main.classroomy.exception.TeacherNotFoundException;
import com.main.classroomy.repository.TeacherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

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

    public void deleteById(Long id) {
        this.teacherRepository.deleteById(id);
    }

}
