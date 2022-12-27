package com.main.classroomy.controlller;

import com.main.classroomy.entity.dto.AssignmentDto;
import com.main.classroomy.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/{id:\\d+}/deadline", method = RequestMethod.PUT)
    public void updateDeadline(@PathVariable Long id, @Valid @RequestBody AssignmentDto assignmentDto) {
        this.postService.updateById(id, assignmentDto);
    }

}
