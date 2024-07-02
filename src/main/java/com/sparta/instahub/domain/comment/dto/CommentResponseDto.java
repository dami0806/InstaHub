package com.sparta.instahub.domain.comment.dto;

import com.sparta.instahub.domain.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long id;
    private String contents;

    public CommentResponseDto(Comment comment) {
        this.id=comment.getId();
        this.contents=comment.getContents();


    }
}
