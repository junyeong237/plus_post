package com.example.plus_assignment.domain.post.controller;

import com.example.plus_assignment.domain.post.dto.request.PostRequestDto;
import com.example.plus_assignment.domain.post.dto.response.PostDetailResponseDto;
import com.example.plus_assignment.domain.post.dto.response.PostPreviewResponseDto;
import com.example.plus_assignment.domain.post.service.Impl.PostServiceImpl;
import com.example.plus_assignment.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl postService;

    @GetMapping
    public Page<PostPreviewResponseDto> getPostAll(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam("sortBy") String sortBy,
        @RequestParam("isAsc") boolean isAsc

    ){

        Page<PostPreviewResponseDto> postAll = postService.getPostAll(page-1, size, sortBy, isAsc);
        return postAll;

    }

    @GetMapping("/dsl")
    public List<PostPreviewResponseDto> getPostListByDsl( // dsl사용해서 상위 몇개만 뽑아서 보여줌
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam("sortBy") String sortBy,
        @RequestParam("isAsc") boolean isAsc

    ){

        List<PostPreviewResponseDto> postAll = postService.getPostListWithPageByDsl(page-1, size, sortBy, isAsc);
        return postAll;

    }

    @GetMapping("/query")
    public Page<PostPreviewResponseDto> getPostListWithSearchAndPaging(
        @RequestParam(value = "search", required = false) String search, //search없으면 그냥 전체조회
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam("sortBy") String sortBy,
        @RequestParam("isAsc") boolean isAsc

    ){

        Page<PostPreviewResponseDto> postAll = postService.getPostListWithSearchAndPaging(page-1, size, sortBy, isAsc,search);
        return postAll;

    }



    @GetMapping("/{postId}")
    public PostDetailResponseDto getPost(
        @PathVariable Long postId
    ){
        return postService.getPost(postId);
    }


    @PostMapping
    public PostDetailResponseDto createPost(
        @RequestBody PostRequestDto postRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){

        PostDetailResponseDto post = postService.createPost(postRequestDto,userDetails.getUser());
        return post;
    }

    @PutMapping("/{postId}")
    public PostDetailResponseDto updatePost(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long postId,
        @RequestBody @Valid PostRequestDto postRequestDto
    ){

        PostDetailResponseDto post = postService.updatePost(userDetails.getUser(),postId,postRequestDto);
        return post;
    }

    @DeleteMapping("/{postId}")
    public void deletePost(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long postId
    ){

        postService.deletePost(userDetails.getUser(),postId);

    }


}
