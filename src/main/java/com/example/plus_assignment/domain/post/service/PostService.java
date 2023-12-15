package com.example.plus_assignment.domain.post.service;

import com.example.plus_assignment.domain.post.dto.request.PostRequestDto;
import com.example.plus_assignment.domain.post.dto.response.PostDetailResponseDto;
import com.example.plus_assignment.domain.post.dto.response.PostPreviewResponseDto;
import com.example.plus_assignment.domain.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;

public interface PostService {

    Page<PostPreviewResponseDto> getPostAll(int page, int size, String sortBy, boolean isAsc);

    PostDetailResponseDto createPost(PostRequestDto postRequestDto, User user);
}
