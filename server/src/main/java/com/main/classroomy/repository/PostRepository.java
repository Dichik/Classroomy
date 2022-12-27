package com.main.classroomy.repository;

import com.main.classroomy.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCourseId(Long id);

    List<Post> findByDeadlineBefore(Timestamp deadline);

}
