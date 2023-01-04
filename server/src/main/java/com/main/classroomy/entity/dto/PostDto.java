package com.main.classroomy.entity.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class PostDto {

    private String title;
    private String description;
    private Long courseId;
    private Date deadline;
    private boolean isDone;
    private String answer;

}
