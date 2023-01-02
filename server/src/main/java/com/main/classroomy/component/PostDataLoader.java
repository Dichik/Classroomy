package com.main.classroomy.component;

import com.main.classroomy.entity.Course;
import com.main.classroomy.entity.Post;
import com.main.classroomy.repository.CourseRepository;
import com.main.classroomy.repository.PostRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@DependsOn({"courseDataLoader"})
public class PostDataLoader implements ApplicationRunner {
    private static final Logger logger = LogManager.getLogger(PostDataLoader.class);

    private final PostRepository postRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public PostDataLoader(PostRepository postRepository, CourseRepository courseRepository) {
        this.postRepository = postRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Course> courses = this.courseRepository.findAll();
        Random random = new Random();
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            Post post = Post.builder()
                    .title("assignment " + i)
                    .description("to do task " + i)
                    .courseId(random.nextLong(courses.size()))
                    .build();
            posts.add(post);
            logger.info("post was added to course with id=" + post.getCourseId());
        }
        this.postRepository.saveAll(posts);
    }

}
