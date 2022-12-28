package com.main.classroomy.service;

import com.main.classroomy.entity.Student;
import com.main.classroomy.entity.dto.StudentDto;
import com.main.classroomy.exception.StudentNotFoundException;
import com.main.classroomy.repository.StudentRepository;
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

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentService(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    public List<Student> getAll() {
        return this.studentRepository.findAll();
    }

    public Optional<Student> getById(Long id) {
        return this.studentRepository.findById(id);
    }

    public Optional<Student> getByEmail(String email) {
        return this.studentRepository.findByEmail(email);
    }

    public Student create(StudentDto studentDto) {
        return this.studentRepository.save(this.modelMapper.map(studentDto, Student.class));
    }

    public Student updateById(Long id, StudentDto studentDto) {
        if (!this.studentRepository.existsById(id)) {
            throw new StudentNotFoundException(String.format("Student with id=%s was not found!", id));
        }
        Student student = this.modelMapper.map(studentDto, Student.class);
        student.setId(id);
        return this.studentRepository.save(student);
    }

    public void deleteById(Long id) {
        if (!this.studentRepository.existsById(id)) {
            throw new StudentNotFoundException(String.format("Student with id=%s was not found!", id));
        }
        this.studentRepository.deleteById(id);
    }

}
