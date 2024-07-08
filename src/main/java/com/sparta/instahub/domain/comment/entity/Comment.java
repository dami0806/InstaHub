package com.sparta.instahub.domain.comment.entity;

import com.github.f4b6a3.ulid.UlidCreator;
import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.comment.dto.CommentRequestDto;
import com.sparta.instahub.common.entity.BaseEntity;
import com.sparta.instahub.domain.like.entity.Like;
import com.sparta.instahub.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
    // 고유 식별자
    @Id
    @Column(columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "uuid2")
    private UUID id = UlidCreator.getMonotonicUlid().toUuid();

    //댓글이 달린 게시물
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();


    @Builder
    public Comment(Post post, User user, String contents) {
        this.post = post;
        this.user = user;
        this.contents = contents;
    }

    public void update(String contents) {
        this.contents = contents;
        this.updatedAt = LocalDateTime.now();
    }
}
