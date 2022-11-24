package com.main.classroomy.service;

import com.main.classroomy.entity.Post;
import com.main.classroomy.entity.dto.PostDto;
import com.main.classroomy.exception.PostNotFoundException;
import com.main.classroomy.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return null;
    }

}
