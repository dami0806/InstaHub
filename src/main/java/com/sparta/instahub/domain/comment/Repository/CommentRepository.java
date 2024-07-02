package com.sparta.instahub.domain.comment.Repository;

import com.sparta.instahub.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
