package com.example.plus_assignment.domain.post.service;

import com.example.plus_assignment.domain.post.dto.request.PostRequestDto;
import com.example.plus_assignment.domain.post.dto.response.PostDetailResponseDto;
import com.example.plus_assignment.domain.post.dto.response.PostPreviewResponseDto;
import com.example.plus_assignment.domain.post.entity.Post;
import com.example.plus_assignment.domain.user.entity.User;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    Page<PostPreviewResponseDto> getPostAll(int page, int size, String sortBy, boolean isAsc);

    PostDetailResponseDto createPost(PostRequestDto postRequestDto, User user, MultipartFile multipartFile)
        throws IOException;

    PostDetailResponseDto getPost(Long postId);

    PostDetailResponseDto updatePost(User user, Long postId, PostRequestDto postRequestDto);

    List<PostPreviewResponseDto> getPostListWithPageByDsl(int page, int size, String sortBy, boolean isAsc);
    //quetydsl 사용, 다만 List를 반환하므로 1페이지에 몇개가 나오는정도로만 설정가능

    Page<PostPreviewResponseDto> getPostListWithSearchAndPaging(int page, int size, String sortBy, boolean isAsc,String search);

    void deletePost(User user, Long postId);
}
