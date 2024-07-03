package com.sparta.instahub.domain.comment.entity;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.comment.dto.CommentRequestDto;
import com.sparta.instahub.common.entity.BaseEntity;
import com.sparta.instahub.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="comment")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //댓글이 달린 게시물
    @ManyToOne
    @JoinColumn(name="post_id",nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @Column(name = "contents", nullable = false)
    private String contents;

    public Comment(CommentRequestDto requestDto, Post post, User user) {
        this.contents=requestDto.getContents();
        this.post=post;
        this.user=user;
    }

    public void update(CommentRequestDto requestDto) {
        this.contents=requestDto.getContents();
    }
}
