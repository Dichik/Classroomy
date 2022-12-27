package com.main.classroomy.service;

import com.main.classroomy.entity.Post;
import com.main.classroomy.entity.dto.AssignmentDto;
import com.main.classroomy.entity.dto.PostDto;
import com.main.classroomy.exception.DeadlineUpdateException;
import com.main.classroomy.exception.PostNotFoundException;
import com.main.classroomy.repository.PostRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PostService {
    private static final Logger logger = LogManager.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PostService(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    public Post getById(Long id) {
        return this.postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post with id=" + id + " was not found!"));
    }

    public void create(Post post) {
        this.postRepository.save(post);
    }

    public void update(Long id, PostDto postDto) {
        Post post = this.modelMapper.map(postDto, Post.class);
        post.setId(id);
        this.postRepository.save(post);
    }

    public List<Post> getByCourseId(Long id) {
        return this.postRepository.findByCourseId(id);
    }

    public List<Post> getAssignmentsForNextWeek() {
        LocalDateTime nowPlusWeek = LocalDateTime.now();
        nowPlusWeek.plus(1, ChronoUnit.WEEKS);
        return this.postRepository.findByDeadlineBefore(Timestamp.valueOf(nowPlusWeek));
    }

    public void updateById(Long id, AssignmentDto assignmentDto) throws DeadlineUpdateException {
        Post post = this.postRepository.findById(id).orElse(null);
        if (post == null) {
            throw new PostNotFoundException(String.format("Post with id=[%s] can't be found.", id));
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (assignmentDto.getDeadline().compareTo(now) <= 0) {
            throw new DeadlineUpdateException("Deadline can't be updated as you are trying to set past date.");
        }
        post.setDeadline(assignmentDto.getDeadline());
        this.postRepository.save(post);
    }
    
}
