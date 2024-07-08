package com.sparta.instahub.domain.comment.service;

import com.sparta.instahub.domain.comment.dto.CommentRequestDto;
import com.sparta.instahub.domain.comment.dto.CommentResponseDto;
import com.sparta.instahub.domain.comment.entity.Comment;
import com.sparta.instahub.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;


public interface CommentService {
    CommentResponseDto createComment(UUID postId, String username, CommentRequestDto requestDto);
    CommentResponseDto updateComment(UUID commentId, String username, CommentRequestDto requestDto);
    void deleteComment(UUID commentId, String username);

    Page<CommentResponseDto> getCommentsByPostId(UUID post, int page, int size, String sortBy);
    Comment getComment(UUID commentId);
    Page<CommentResponseDto> getLikedComments(UUID userId, Pageable pageable);
}
