package com.example.plus_assignment.domain.post.service.Impl;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import com.example.plus_assignment.domain.post.dto.request.PostRequestDto;
import com.example.plus_assignment.domain.post.dto.response.PostDetailResponseDto;
import com.example.plus_assignment.domain.post.dto.response.PostPreviewResponseDto;
import com.example.plus_assignment.domain.post.entity.Post;
import com.example.plus_assignment.domain.post.exception.ForbiddenAccessPostException;
import com.example.plus_assignment.domain.post.exception.NotFoundPostException;
import com.example.plus_assignment.domain.post.exception.PostErrorCode;
import com.example.plus_assignment.domain.post.repository.PostRepository;
import com.example.plus_assignment.domain.post.service.PostService;
import com.example.plus_assignment.domain.user.entity.User;
import com.example.plus_assignment.domain.user.exception.NotFoundUserException;
import com.example.plus_assignment.domain.user.exception.UserErrorCode;
import com.example.plus_assignment.domain.user.repository.UserRepositry;
import com.example.plus_assignment.global.s3.S3util;
import com.example.plus_assignment.global.s3.S3util.ImagePath;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional//(propagation = REQUIRES_NEW)
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepositry userRepositry;
    private final S3util s3util;

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
    public PostDetailResponseDto createPost(PostRequestDto postRequestDto, User user)
        throws IOException {

        User findUser = findUserById(user.getId());

//        if(!(multipartFile.isEmpty() || multipartFile == null)){
//            String imageName = s3util.uploadImage(multipartFile, ImagePath.MENU);
//            String imagePath = s3util.getImagePath(imageName, ImagePath.MENU);
//        }


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

        User findUser = findUserById(user.getId());

        Post post = findPostById(postId);

        validatePostUser(findUser,post);

        post.update(postRequestDto);


        return new PostDetailResponseDto(post);
    }

    @Override
    public void deletePost(User user, Long postId) {

        User findUser = findUserById(user.getId());
        Post post = findPostById(postId);

        validatePostUser(findUser,post);

        postRepository.delete(post);
    }

    private User findUserById(Long id){
        return userRepositry.findById(id)
            .orElseThrow(()->new NotFoundUserException(UserErrorCode.NOT_FOUND_USER));
    }

    private Post findPostById(Long id){
        return postRepository.findById(id)
            .orElseThrow(()->new NotFoundPostException(PostErrorCode.NOT_FOUND_POST));

    }

    private void validatePostUser(User user, Post post){
        if(!post.getUser().equals(user)){
            throw new ForbiddenAccessPostException(PostErrorCode.FORBIDDEN_ACCESS_POST);
        }
    }
}
