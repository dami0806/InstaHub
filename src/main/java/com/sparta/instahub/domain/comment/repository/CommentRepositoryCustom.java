package com.sparta.instahub.domain.comment.repository;

import com.sparta.instahub.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CommentRepositoryCustom {
    Page<Comment> findAllByPostId(UUID postId, Pageable pageable);
}
