package com.main.classroomy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    private Long id;

    private String name;

    private String description;

    @OneToMany
    private List<Teacher> teachers = new ArrayList<>();

}