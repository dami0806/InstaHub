package com.sparta.instahub.domain.comment.service;

import com.sparta.instahub.domain.comment.dto.CommentRequestDto;
import com.sparta.instahub.domain.comment.dto.CommentResponseDto;

import java.util.List;
import java.util.UUID;


public interface CommentService {
    CommentResponseDto createComment(UUID postId, String username, CommentRequestDto requestDto);
    CommentResponseDto updateComment(UUID commentId, String username, CommentRequestDto requestDto);
    void deleteComment(UUID commentId, String username);

}
