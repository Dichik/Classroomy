package com.main.classroomy.component;

import com.main.classroomy.entity.Course;
import com.main.classroomy.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseDataLoader implements ApplicationRunner {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseDataLoader(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            Course course = Course.builder()
                    .name("course" + i)
                    .description("here we learn how to use " + i)
                    .build();
            courses.add(course);
        }
        this.courseRepository.saveAll(courses);
    }

}
