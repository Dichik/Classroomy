package com.main.classroomy.repository;

import com.main.classroomy.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCourseId(Long courseId);

    List<Post> findByCourseIdAndDeadlineBefore(Long courseId, Date deadline);

    List<Post> findAllByCourseIdAndDeadlineNotNull(Long courseId);

    List<Post> findByDeadlineBefore(Date deadline);

}
