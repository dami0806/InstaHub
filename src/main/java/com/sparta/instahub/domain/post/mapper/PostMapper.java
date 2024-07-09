package com.sparta.instahub.domain.post.mapper;

import com.sparta.instahub.domain.comment.mapper.CommentMapper;
import com.sparta.instahub.domain.post.dto.PostResponseDto;
import com.sparta.instahub.domain.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {CommentMapper.class})
public interface PostMapper {

    @Mappings({
            @Mapping(source = "post.user.username", target = "username"),
            @Mapping(source = "post.comments", target = "comments"),
            @Mapping(source = "likeCount", target = "likeCount")
    })
    PostResponseDto toPostResponseDto(Post post, long likeCount);
}
