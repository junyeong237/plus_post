package com.example.plus_assignment.domain.post.repository;


import com.example.plus_assignment.domain.post.entity.Post;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepositoryCustom {

    List<Post> getPostListWithPage(Pageable pageable);

    Page<Post> searchPostListWithSearchAndPaging(String search, Pageable pageable);
}
