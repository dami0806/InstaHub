package com.sparta.instahub.domain.post.dto;

import com.sparta.instahub.domain.comment.dto.CommentResponseDto;
import com.sparta.instahub.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter //Lombok
// 게시물 응답
public class PostResponseDto {
    private final UUID id; // 게시물 ID
    private final String title; // 게시물 제목
    private final String content; // 게시물 내용
    private final String username; // 게시물 작성자
    private final String imageUrl; // 게시물 이미지
    private final LocalDateTime createdAt; // 생성일시
    private final LocalDateTime updatedAt; // 수정일시
    private List<CommentResponseDto> comments;

    public PostResponseDto(final Post post) {

        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUser().getUsername();
        this.imageUrl = post.getImageUrl();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.comments = post.getComments().stream()
                .map(CommentResponseDto::new).
                collect(Collectors.toList());
    }

    @Builder
    public PostResponseDto(UUID id, String title, String content, String imageUrl, String username, LocalDateTime createdAt, LocalDateTime updatedAt, List<CommentResponseDto> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.comments = comments;
    }
}


