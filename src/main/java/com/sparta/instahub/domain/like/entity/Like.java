package com.sparta.instahub.domain.like.entity;

import com.github.f4b6a3.ulid.UlidCreator;
import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.comment.entity.Comment;
import com.sparta.instahub.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "post_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LikeType type;

    @Builder
    public Like(User user, Post post, Comment comment, LikeType type) {
        this.user = user;
        this.post = post;
        this.comment = comment;
        this.type = type;
    }
}
