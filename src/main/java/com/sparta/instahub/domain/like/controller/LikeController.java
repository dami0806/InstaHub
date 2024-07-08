package com.sparta.instahub.domain.like.controller;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.auth.service.UserService;
import com.sparta.instahub.domain.comment.dto.CommentResponseDto;
import com.sparta.instahub.domain.like.service.LikeService;
import com.sparta.instahub.domain.post.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final UserService userService;

    // 게시글 좋아요
    @PostMapping("/posts/{postId}")
    public ResponseEntity<Void> likePost(@PathVariable UUID postId,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByName(userDetails.getUsername());
        likeService.likePost(user.getId(), postId);
        return ResponseEntity.ok().build();
    }

    //게시글 좋아요 취소
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> unlikePost(@PathVariable UUID postId,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByName(userDetails.getUsername());
        likeService.unlikePost(user.getId(), postId);
        return ResponseEntity.ok().build();
    }

    // 댓글 좋아요
    @PostMapping("/comments/{commentId}")
    public ResponseEntity<Void> likeComment(@PathVariable UUID commentId,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByName(userDetails.getUsername());
        likeService.likeComment(user.getId(), commentId);
        return ResponseEntity.ok().build();
    }

    //댓글 좋아요 취소
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> unlikeComment(@PathVariable UUID commentId,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByName(userDetails.getUsername());
        likeService.unlikeComment(user.getId(), commentId);
        return ResponseEntity.ok().build();
    }
}
