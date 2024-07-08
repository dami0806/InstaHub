package com.sparta.instahub.domain.like.service;

import com.sparta.instahub.domain.comment.dto.CommentResponseDto;
import com.sparta.instahub.domain.post.dto.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


public interface LikeService {

    // userRepo에서 좋아요
    void likePost(UUID userId, UUID postId);
    void unlikePost(UUID userId, UUID postId);

    void likeComment(UUID userId, UUID commentId);
    void unlikeComment(UUID userId, UUID commentId);

    long countLikesByPostId(UUID postId);

}
