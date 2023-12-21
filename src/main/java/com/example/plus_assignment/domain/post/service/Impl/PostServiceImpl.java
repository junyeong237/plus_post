package com.example.plus_assignment.domain.post.service.Impl;

import com.example.plus_assignment.domain.post.dto.request.PostRequestDto;
import com.example.plus_assignment.domain.post.dto.response.PostDetailResponseDto;
import com.example.plus_assignment.domain.post.dto.response.PostPreviewResponseDto;
import com.example.plus_assignment.domain.post.entity.Post;
import com.example.plus_assignment.domain.post.repository.PostRepository;
import com.example.plus_assignment.domain.post.service.PostService;
import com.example.plus_assignment.domain.user.entity.User;
import com.example.plus_assignment.domain.user.exception.NotFoundUserException;
import com.example.plus_assignment.domain.user.exception.UserErrorCode;
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
        //quetyDSL 미사용
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);


        Page<Post> productList;
        productList =  postRepository.findAll(pageable);
        return productList.map(PostPreviewResponseDto::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostPreviewResponseDto> getPostListWithPageByDsl(int page, int size, String sortBy, boolean isAsc) {
        //quetyDSL 사용해서
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        List<Post> postList;
        postList =  postRepository.getPostListWithPage(pageable);
        return postList.stream()
            .map(PostPreviewResponseDto::new)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostPreviewResponseDto> getPostListWithSearchAndPaging(int page, int size, String sortBy,
        boolean isAsc, String search) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Post> postList;
        postList =  postRepository.searchPostListWithSearchAndPaging(search,pageable);
        return postList.map(PostPreviewResponseDto::new);
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

    @Override
    public PostDetailResponseDto getPost(Long postId) {

        Post post = findPostById(postId);

        return new PostDetailResponseDto(post);

    }

    @Override
    public PostDetailResponseDto updatePost(User user, Long postId,
        PostRequestDto postRequestDto) {

        Post post = findPostById(postId);

        validatePostUser(user,post);

        post.update(postRequestDto);


        return new PostDetailResponseDto(post);
    }

    @Override
    public void deletePost(User user, Long postId) {
        Post post = findPostById(postId);

        validatePostUser(user,post);

        postRepository.delete(post);
    }

    private User findUserById(Long id){
        return userRepositry.findById(id)
            .orElseThrow(()->new NotFoundUserException(UserErrorCode.NOT_FOUND_USER));
    }

    private Post findPostById(Long id){
        return postRepository.findById(id)
            .orElseThrow(()->new IllegalArgumentException("해당하는 게시글이 없습니다."));

    }

    private void validatePostUser(User user, Post post){
        if(!post.getUser().equals(user)){
            throw new IllegalArgumentException("알맞지않은 유저입니다.");
        }
    }
}
