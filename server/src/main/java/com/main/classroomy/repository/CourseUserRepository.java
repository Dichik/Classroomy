package com.main.classroomy.repository;

import com.main.classroomy.entity.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseUserRepository extends JpaRepository<CourseUser, Long> {
    List<CourseUser> findAllByUsername(String username);
}
