package com.example.plus_assignment.domain.post.repository;

import com.example.plus_assignment.domain.post.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>, PostRepositoryCustom  {

    Page<Post> findAll(Pageable pageable);

    List<Post> findAll();

    List<Post> findByCreatedAtBefore(LocalDateTime dateTime);
}
