package com.sparta.instahub.domain.post.controller;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.auth.service.UserService;
import com.sparta.instahub.domain.comment.dto.CommentResponseDto;
import com.sparta.instahub.domain.comment.service.CommentService;
import com.sparta.instahub.domain.post.dto.PostRequestDto;
import com.sparta.instahub.domain.post.dto.PostResponseDto;
import com.sparta.instahub.domain.post.entity.Post;
import com.sparta.instahub.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final CommentService commentService;
    private final UserService userService;

    /**
     * 모든 게시물 조회 요청 처리
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "0") int commentPage,
            @RequestParam(defaultValue = "3") int commentSize,
            @RequestParam(defaultValue = "createdAt") String commentSortBy
    ) {

        List<PostResponseDto> postResponseDtos = postService.getAllPosts(page, size, sortBy)
                .stream()
                .map(PostResponseDto -> {
                    Page<CommentResponseDto> commentsPage = commentService.getCommentsByPostId(
                            PostResponseDto.getId(),
                            commentPage,
                            commentSize,
                            commentSortBy
                    );

                    PostResponseDto.updateComments(commentsPage.getContent());
                    return PostResponseDto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(postResponseDtos);
    }

    // ID로 게시물 조회 요청 처리
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable UUID postId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "3") int size,
                                                       @RequestParam(defaultValue = "createdAt") String sortBy,
                                                       @RequestParam(defaultValue = "0") int commentPage,
                                                       @RequestParam(defaultValue = "3") int commentSize,
                                                       @RequestParam(defaultValue = "createdAt") String commentSortBy

    ) {
        PostResponseDto postResponseDto = postService.getPostById(postId);

        Page<CommentResponseDto> commentsPage = commentService.getCommentsByPostId(postId, commentPage, commentSize, commentSortBy);
        postResponseDto.updateComments(commentsPage.getContent());
        return ResponseEntity.ok(postResponseDto);
    }

    // 새 게시물 생성 요청 처리
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@ModelAttribute PostRequestDto postRequestDto,
                                                      @AuthenticationPrincipal UserDetails userDetails) {
        PostResponseDto post = postService.createPost(
                postRequestDto.getTitle(),
                postRequestDto.getContent(),
                postRequestDto.getImage(),
                userDetails.getUsername());

        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    // 게시물 수정 요청 처리
    @PatchMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable UUID id,
                                                      @ModelAttribute PostRequestDto postRequestDto,
                                                      @AuthenticationPrincipal UserDetails userDetails) {
        PostResponseDto post = postService.updatePost(id,
                postRequestDto.getTitle(),
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

    // 팔로우들의  전체 게시물 보기
    @GetMapping("/followers")
    public ResponseEntity<Page<PostResponseDto>> getFollowedPosts(@AuthenticationPrincipal UserDetails userDetails,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "5") int size) {
        User user = getCurrentUserId(userDetails);
        Pageable pageable = PageRequest.of(page, size);

        Page<PostResponseDto> posts = postService.getFollowerPosts(user.getId(), pageable);
        return ResponseEntity.ok(posts);
    }
    private User getCurrentUserId(UserDetails userDetails) {
        return userService.getUserByName(userDetails.getUsername());
    }
}
