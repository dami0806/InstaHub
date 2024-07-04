package com.sparta.instahub.domain.comment.dto;

import com.sparta.instahub.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class CommentResponseDto {
    final private UUID id;
    final private String contents;
    final private String username;
    final private  LocalDateTime createdAt; // 생성일시
    final private LocalDateTime updatedAt; // 수정일시
    private int likeCount;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.username = comment.getUser().getUsername();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.likeCount = comment.getLikes().size();
    }

    public static CommentResponseDto commentResponseDto(Comment comment) {
        return new CommentResponseDto(comment);
    }
}
