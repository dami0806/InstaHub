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
@Table(name = "likes")
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


    @Column(name = "post_id", columnDefinition = "BINARY(16)", nullable = true)
    private UUID postId;

    @Column(name = "comment_id", columnDefinition = "BINARY(16)", nullable = true)
    private UUID commentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LikeType type;

    @Builder
    public Like(User user, UUID post, UUID comment, LikeType type) {
        this.user = user;
        this.postId = post;
        this.commentId = comment;
        this.type = type;
    }
}
