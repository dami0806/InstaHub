package com.sparta.instahub.domain.like.entity;

import com.github.f4b6a3.ulid.UlidCreator;
import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.comment.entity.Comment;
import com.sparta.instahub.domain.post.entity.Post;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "post_like")
public class Like {
    // 고유 식별자
    @Id
    @Column(columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "uuid2")
    private UUID id = UlidCreator.getMonotonicUlid().toUuid();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = true)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = true)
    private Comment comment;






}
