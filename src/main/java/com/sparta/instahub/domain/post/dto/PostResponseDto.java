package com.sparta.instahub.domain.post.dto;

import com.sparta.instahub.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter //Lombok
@Builder //Lombok
@AllArgsConstructor
// 게시물 응답
public class PostResponseDto {
    private UUID id; // 게시물 ID
    private String title; // 게시물 제목
    private String content; // 게시물 내용
    private String author; // 게시물 작성자
    private String imageUrl; // 게시물 이미지
    private LocalDateTime createdAt; // 생성일시
    private LocalDateTime updatedAt; // 수정일시

    public PostResponseDto(final Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getUser().getUsername();
        this.imageUrl = post.getImageUrl();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}


