package com.main.classroomy.entity.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AssignmentDto {

    private String title;
    private Timestamp deadline;

}
