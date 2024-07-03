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
    private final UUID id; // 게시물 ID
    private final String title; // 게시물 제목
    private final String content; // 게시물 내용
    private final String author; // 게시물 작성자
    private final String imageUrl; // 게시물 이미지
    private final LocalDateTime createdAt; // 생성일시
    private final LocalDateTime updatedAt; // 수정일시

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


