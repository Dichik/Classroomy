package com.main.classroomy.component;

import com.main.classroomy.entity.Student;
import com.main.classroomy.repository.StudentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentDataLoader implements ApplicationRunner {
    private static final Logger logger = LogManager.getLogger(StudentDataLoader.class);

    private final StudentRepository studentRepository;

    @Autowired
    public StudentDataLoader(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Started to populate student data...");

        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            Student student = Student.builder()
                    .email("student" + i + "@student.com")
                    .firstName("student" + i)
                    .secondName("student" + i)
                    .build();
            students.add(student);
        }
        this.studentRepository.saveAll(students);

        logger.info("Student data was populated successfully!");
    }

}
