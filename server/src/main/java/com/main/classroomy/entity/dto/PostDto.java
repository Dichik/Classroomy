package com.main.classroomy.entity.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostDto {

    private String title;
    private String description;
    private Long courseId;
    private Timestamp deadline;
    private boolean isDone;

}
