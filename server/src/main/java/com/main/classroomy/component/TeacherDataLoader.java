package com.main.classroomy.component;

import com.main.classroomy.entity.Teacher;
import com.main.classroomy.repository.TeacherRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TeacherDataLoader implements ApplicationRunner {
    private static final Logger logger = LogManager.getLogger(TeacherDataLoader.class);

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherDataLoader(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Started to populate teacher data...");

        Teacher teacher = Teacher.builder()
                .email("teacher@teacher.com")
                .firstName("Jonh")
                .secondName("Petrenko")
                .build();
        this.teacherRepository.save(teacher);

        logger.info("Teacher data was successfully populated!");
    }

}
