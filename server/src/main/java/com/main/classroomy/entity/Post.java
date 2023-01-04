package com.main.classroomy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 50)
    private String title;

    @Size(min = 3, max = 255)
    private String description;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "course_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private Long courseId;

    private Date deadline;

    @Builder.Default
    private boolean isDone = false;

    @Size(max = 255)
    private String answer; // FIXME create new table for that

}