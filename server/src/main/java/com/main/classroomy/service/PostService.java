package com.main.classroomy.service;

import com.main.classroomy.entity.Post;
import com.main.classroomy.entity.dto.PostDto;
import com.main.classroomy.exception.DeadlineUpdateException;
import com.main.classroomy.exception.PostNotFoundException;
import com.main.classroomy.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PostService(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<Post> getById(Long id) {
        return this.postRepository.findById(id);
    }

    public Post create(Post post) {
        return this.postRepository.save(post);
    }

    public Post update(Long id, PostDto postDto) {
        Post post = this.postRepository.findById(id).orElse(null);
        if (post == null) {
            throw new EntityNotFoundException("Post with id=" + id + " was not found.");
        }
        post.setAnswer(postDto.getAnswer());
        return this.postRepository.save(post);
    }

    public List<Post> getByCourseId(Long id) {
        return this.postRepository.findByCourseId(id);
    }

    public List<Post> getAssignmentsForNextWeek(Long id) {
        LocalDate nowPlusWeek = LocalDate.now();
        nowPlusWeek = nowPlusWeek.plus(1, ChronoUnit.WEEKS);
        return this.postRepository.findByCourseIdAndDeadlineBefore(id, Date.valueOf(nowPlusWeek));
    }

    public List<Post> getAssignmentsForNextWeek() {
        LocalDate nowPlusWeek = LocalDate.now();
        nowPlusWeek = nowPlusWeek.plus(1, ChronoUnit.WEEKS);
        return this.postRepository.findByDeadlineBefore(Date.valueOf(nowPlusWeek)); // TODO get course name as well in query
    }

    public void updateById(Long id, PostDto postDto) throws DeadlineUpdateException {
        Post post = this.postRepository.findById(id).orElse(null);
        if (post == null) {
            throw new PostNotFoundException(String.format("Post with id=[%s] can't be found.", id));
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (postDto.getDeadline().compareTo(now) <= 0) {
            throw new DeadlineUpdateException("Deadline can't be updated as you are trying to set past date.");
        }
        post.setDeadline(postDto.getDeadline());
        this.postRepository.save(post);
    }

    public List<Post> updatePosts(List<Post> posts) {
        return this.postRepository.saveAll(posts);
    }

    public List<Post> getAssignmentsWithDeadlines(Long courseId) {
        return this.postRepository.findAllByCourseIdAndDeadlineNotNull(courseId);
    }
}
