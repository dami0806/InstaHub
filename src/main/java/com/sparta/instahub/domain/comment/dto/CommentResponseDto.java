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

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.username = comment.getUser().getUsername();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }

    public static CommentResponseDto commentResponseDto(Comment comment) {
        return new CommentResponseDto(comment);
    }

    public static CommentResponseDto from(UUID id, String contents, String username, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new CommentResponseDto(id, contents, username, createdAt, updatedAt);
    }

    private CommentResponseDto(UUID id, String contents, String username, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.contents = contents;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
