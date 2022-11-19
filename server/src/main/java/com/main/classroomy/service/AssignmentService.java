package com.main.classroomy.service;

import com.main.classroomy.entity.Assignment;
import com.main.classroomy.exception.AssignmentNotFoundException;
import com.main.classroomy.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    @Autowired
    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public Assignment getAssignmentById(Long id) {
        return this.assignmentRepository.findById(id)
                .orElseThrow(() -> new AssignmentNotFoundException("Assignment with id=" + id + " was not found!"));
    }

}
