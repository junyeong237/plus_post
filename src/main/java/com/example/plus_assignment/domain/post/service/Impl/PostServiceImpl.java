package com.example.plus_assignment.domain.post.service.Impl;

import com.example.plus_assignment.domain.post.dto.request.PostRequestDto;
import com.example.plus_assignment.domain.post.dto.response.PostDetailResponseDto;
import com.example.plus_assignment.domain.post.dto.response.PostPreviewResponseDto;
import com.example.plus_assignment.domain.post.entity.Post;
import com.example.plus_assignment.domain.post.repository.PostRepository;
import com.example.plus_assignment.domain.post.service.PostService;
import com.example.plus_assignment.domain.user.entity.User;
import com.example.plus_assignment.domain.user.repository.UserRepositry;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepositry userRepositry;

    @Override
    @Transactional(readOnly = true)
    public Page<PostPreviewResponseDto> getPostAll(int page, int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);


        Page<Post> productList;
        productList =  postRepository.findAllByOrderByCreatedAtDesc(pageable);
        return productList.map(PostPreviewResponseDto::new);
    }

    @Override
    public PostDetailResponseDto createPost(PostRequestDto postRequestDto, User user) {

        User findUser = findUserById(user.getId());

        Post post = Post.builder()
            .title(postRequestDto.getTitle())
            .content(postRequestDto.getContent())
            .user(findUser)
            .build();

        postRepository.save(post);

        return new PostDetailResponseDto(post);


    }

    private User findUserById(Long id){
        return userRepositry.findById(id)
            .orElseThrow(()->new IllegalArgumentException("해당하는 유저가 없습니다."));
    }
}
