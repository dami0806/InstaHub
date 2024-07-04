package com.sparta.instahub.domain.comment.controller;

import com.sparta.instahub.domain.comment.dto.CommentRequestDto;
import com.sparta.instahub.domain.comment.dto.CommentResponseDto;
import com.sparta.instahub.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
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

    // 작성
    @PostMapping("{postId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable UUID postId,
                                                            @AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestBody CommentRequestDto requestDto){
        CommentResponseDto responseDto = commentService.createComment(postId, userDetails.getUsername(), requestDto);
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }
    // 댓글 수정
    @PatchMapping("comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable UUID commentId,
                                                            @AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestBody CommentRequestDto requestDto) {
        CommentResponseDto responseDto = commentService.updateComment(commentId, userDetails.getUsername(), requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID commentId,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        commentService.deleteComment(commentId, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
}
