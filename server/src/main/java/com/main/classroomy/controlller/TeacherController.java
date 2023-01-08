package com.main.classroomy.controlller;

import com.main.classroomy.entity.User;
import com.main.classroomy.entity.dto.UserDto;
import com.main.classroomy.exception.TeacherNotFoundException;
import com.main.classroomy.service.TeacherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    private static final Logger logger = LogManager.getLogger(TeacherController.class);

    private final TeacherService teacherService;
    private final ModelMapper modelMapper;

    @Autowired
    public TeacherController(TeacherService teacherService, ModelMapper modelMapper) {
        this.teacherService = teacherService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
    @RequestMapping(value = "/{id:[\\d+]}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        User user = this.teacherService.getById(id)
                .orElseThrow(() -> new TeacherNotFoundException(String.format("Teacher with id=%s was not found.", id)));
        return new ResponseEntity<>(this.modelMapper.map(user, UserDto.class), HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('TEACHER')")
//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseEntity<?> create(@Valid @RequestBody UserDto userDto) {
//        User user = this.teacherService.create(userDto);
//        if (user == null) {
//            logger.warn("Something went wrong, couldn't create teacher...");
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity<>(this.modelMapper.map(user, UserDto.class), HttpStatus.CREATED);
//    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/{id:[\\d+]}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        this.teacherService.deleteById(id);
        return new ResponseEntity<>("Teacher was successfully deleted!", HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @RequestMapping(value = "/{id:[\\d+]}", method = RequestMethod.PUT)
    public ResponseEntity<UserDto> update(@PathVariable Long id, @Valid @RequestBody UserDto teacherDto) {
        User user = this.teacherService.updateById(id, teacherDto);
        return new ResponseEntity<>(this.modelMapper.map(user, UserDto.class), HttpStatus.OK);
    }

}
