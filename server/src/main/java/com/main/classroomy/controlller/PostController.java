package com.main.classroomy.controlller;

import com.main.classroomy.entity.dto.PostDto;
import com.main.classroomy.exception.DeadlineUpdateException;
import com.main.classroomy.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> updateDeadline(@PathVariable Long id, @Valid @RequestBody PostDto postDto) {
        try {
            this.postService.updateById(id, postDto);
            return new ResponseEntity<>("Deadline was updated!", HttpStatus.OK);
        } catch (DeadlineUpdateException e) {
            logger.warn("Error while update deadline, please see: " + e);
            return new ResponseEntity<>("Deadline was not updated.", HttpStatus.NOT_FOUND);
        }
    }

}
