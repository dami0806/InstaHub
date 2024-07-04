package com.sparta.instahub.domain.like.repository;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.comment.entity.Comment;
import com.sparta.instahub.domain.like.entity.Like;
import com.sparta.instahub.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID> {

    Optional<Like> findByUserAndPost(User user, Post post);

    Optional<Like> findByUserAndComment(User user, Comment comment);

    boolean existsByUserAndPost(User user, Post post);

    boolean existsByUserAndComment(User user, Comment comment);

    List<Like> findByUserAndPostIsNotNull(User user);
    List<Like> findByUserAndCommentIsNotNull(User user);

}
