package com.sparta.instahub.domain.comment.controller;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.auth.service.UserService;
import com.sparta.instahub.domain.comment.dto.CommentRequestDto;
import com.sparta.instahub.domain.comment.dto.CommentResponseDto;
import com.sparta.instahub.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    // 작성
    @PostMapping("{postId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable UUID postId,
                                                            @AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestBody CommentRequestDto requestDto) {
        User user = getCurrentUserId(userDetails);

        CommentResponseDto responseDto = commentService.createComment(postId, user.getUsername(), requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 댓글 수정
    @PatchMapping("comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable UUID commentId,
                                                            @AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestBody CommentRequestDto requestDto) {
        User user = getCurrentUserId(userDetails);
        CommentResponseDto responseDto = commentService.updateComment(commentId, user.getUsername(), requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID commentId,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        User user = getCurrentUserId(userDetails);
        commentService.deleteComment(commentId, user.getUsername());
        return ResponseEntity.ok().build();
    }

    // 좋아요한 댓글 보기
    @GetMapping("comments/liked")
    public ResponseEntity<Page<CommentResponseDto>> getLikedComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = getCurrentUserId(userDetails);
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentResponseDto> likedComments = commentService.getLikedComments(user.getId(), pageable);
        return ResponseEntity.ok(likedComments);
    }

    private User getCurrentUserId(UserDetails userDetails) {
        return userService.getUserByName(userDetails.getUsername());
    }
}
