package com.sparta.instahub.domain.comment.repository;

import com.sparta.instahub.domain.comment.dto.CommentResponseDto;
import com.sparta.instahub.domain.comment.entity.Comment;
import com.sparta.instahub.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID>,CommentRepositoryCustom {
    Page<Comment> findByPostId(UUID postId, Pageable pageable);

}



