package com.sparta.instahub.domain.post.controller;

import com.sparta.instahub.domain.post.dto.PostRequestDto;
import com.sparta.instahub.domain.post.dto.PostResponseDto;
import com.sparta.instahub.domain.post.entity.Post;
import com.sparta.instahub.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// 게시물 관련 요청을 처리하는 REST 컨트롤러
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    /**
     * 모든 게시물 조회 요청 처리
     * @return
     */
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<PostResponseDto> postResponseDtos = postService.getAllPosts();
        return ResponseEntity.ok(postResponseDtos);
    }

    // ID로 게시물 조회 요청 처리
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable UUID id) {
        PostResponseDto postResponseDto = postService.getPostById(id);
        return ResponseEntity.ok(postResponseDto);
    }

    // 새 게시물 생성 요청 처리
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@ModelAttribute PostRequestDto postRequestDto,
                                                      @AuthenticationPrincipal UserDetails userDetails) {
        PostResponseDto post = postService.createPost(postRequestDto.getTitle(),
                postRequestDto.getContent(),
                postRequestDto.getImage(),
                userDetails.getUsername());

//        PostResponseDto postResponseDto = PostResponseDto.builder()
//                .id(post.getId())
//                .title(post.getTitle())
//                .content(post.getContent())
//                .author(post.getAuthor())
//                .imageUrl(post.getImageUrl())
//                .createdAt(post.getCreatedAt())
//                .updatedAt(post.getUpdatedAt())
//                .build();
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    // 게시물 수정 요청 처리
    @PatchMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable UUID id,
                                                      @ModelAttribute PostRequestDto postRequestDto,
                                                      @AuthenticationPrincipal UserDetails userDetails){
        PostResponseDto post = postService.updatePost(id, postRequestDto.getTitle(),
                postRequestDto.getContent(),
                postRequestDto.getImage(),
                userDetails.getUsername());

        return new ResponseEntity<>(post, HttpStatus.OK);
    }


    // 게시물 삭제 요청 처리
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails) {
        postService.deletePost(id, userDetails.getUsername()); // 게시물 삭제
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
