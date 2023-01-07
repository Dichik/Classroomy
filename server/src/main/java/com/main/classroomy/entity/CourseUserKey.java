package com.main.classroomy.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class CourseUserKey implements Serializable {

    @Column(name = "course_id")
    Long courseId;

    @Column(name = "user_id")
    Long userId;

}