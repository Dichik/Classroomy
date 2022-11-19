package com.main.classroomy.service;

import com.main.classroomy.exception.PostNotFoundException;
import com.main.classroomy.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Assignment getAssignmentById(Long id) {
        return this.postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Assignment with id=" + id + " was not found!"));
    }

}
