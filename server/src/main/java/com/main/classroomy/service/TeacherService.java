package com.main.classroomy.service;

import com.main.classroomy.entity.Teacher;
import com.main.classroomy.entity.dto.TeacherDto;
import com.main.classroomy.exception.TeacherNotFoundException;
import com.main.classroomy.repository.TeacherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository, ModelMapper modelMapper) {
        this.teacherRepository = teacherRepository;
        this.modelMapper = modelMapper;
    }

    public Teacher getById(Long id) {
        return this.teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(String.format("Teacher with id=%s was not found.", id)));
    }

    public Teacher create(TeacherDto teacherDto) {
        return this.teacherRepository.save(
                this.modelMapper.map(teacherDto, Teacher.class)
        );
    }

    public void deleteById(Long id) {
        this.teacherRepository.deleteById(id);
    }

    public void updateById(Long id, TeacherDto teacherDto) {
        if (!this.teacherRepository.existsById(id)) {
            throw new TeacherNotFoundException(String.format("Teacher with id=%s was not found.", id));
        }
        Teacher teacher = this.modelMapper.map(teacherDto, Teacher.class);
        teacher.setId(id);
        this.teacherRepository.save(teacher);
    }

}
