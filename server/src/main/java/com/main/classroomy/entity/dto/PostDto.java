package com.main.classroomy.entity.dto;

import com.main.classroomy.entity.Course;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostDto {

    private String title;
    private String description;
    private Course course;
    private Timestamp deadline;
    private boolean isDone;

}
