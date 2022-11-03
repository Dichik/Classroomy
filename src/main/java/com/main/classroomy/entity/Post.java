package com.main.classroomy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    private Long id;

    private Long teacherId;

    private Long courseId;

    private String title;

    private String text;

    private String attachment; // TODO new class/entity?

    private LocalTime deadline;

}