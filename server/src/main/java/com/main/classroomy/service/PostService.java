package com.main.classroomy.service;

import com.main.classroomy.entity.Post;
import com.main.classroomy.entity.dto.PostDto;
import com.main.classroomy.exception.DeadlineUpdateException;
import com.main.classroomy.exception.PostNotFoundException;
import com.main.classroomy.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PostService {

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

    public Post create(Post post) {
        return this.postRepository.save(post);
    }

    public Post update(Long id, Post post) {
        post.setId(id);
        return this.postRepository.save(post);
    }

    public List<Post> getByCourseId(Long id) {
        return this.postRepository.findByCourseId(id);
    }

    public List<Post> getAssignmentsForNextWeek(Long id) {
        LocalDateTime nowPlusWeek = LocalDateTime.now();
        nowPlusWeek.plus(1, ChronoUnit.WEEKS);
        return this.postRepository.findByCourseIdAndDeadlineBefore(id, Timestamp.valueOf(nowPlusWeek));
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

}
