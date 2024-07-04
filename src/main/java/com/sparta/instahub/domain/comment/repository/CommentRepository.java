package com.sparta.instahub.domain.comment.repository;

import com.sparta.instahub.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}