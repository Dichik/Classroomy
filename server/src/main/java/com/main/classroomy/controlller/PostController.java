package com.main.classroomy.controlller;

import com.main.classroomy.entity.Post;
import com.main.classroomy.entity.dto.AssignmentDto;
import com.main.classroomy.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostController {
    private static final Logger logger = LogManager.getLogger(PostController.class);

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PatchMapping("/{id:\\d+}")
    public void updateDeadline(@PathVariable Long id, @Valid AssignmentDto assignmentDto) {
        this.postService.updateById(id, assignmentDto);
    }

}
