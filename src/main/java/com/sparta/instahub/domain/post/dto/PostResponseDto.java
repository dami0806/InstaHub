package com.sparta.instahub.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
// 게시물 응답
public class PostResponseDto {
    private UUID id; // 게시물 ID
    private String title; // 게시물 제목
    private String content; // 게시물 내용
    private String author; // 게시물 작성자
    private String imageUrl; // 게시물 이미지
    private LocalDateTime createdAt; // 생성일시
    private LocalDateTime updatedAt; // 수정일시

    @Builder
    public PostResponseDto(UUID id, String title, String content, String author, String imageUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

