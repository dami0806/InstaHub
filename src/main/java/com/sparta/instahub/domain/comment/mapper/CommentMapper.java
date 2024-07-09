package com.sparta.instahub.domain.comment.mapper;

import com.sparta.instahub.domain.comment.dto.CommentResponseDto;
import com.sparta.instahub.domain.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "user.username", target = "username")
    CommentResponseDto toCommentResponseDto(Comment comment);

   // Comment toCommentEntity(CommentResponseDto commentResponseDto);
}