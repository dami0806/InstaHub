package com.sparta.instahub.domain.follow.mapper;

import com.sparta.instahub.domain.follow.dto.FollowResponseDto;
import com.sparta.instahub.domain.follow.entity.Follow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FollowMapper {
    @Mapping(source = "follower.username", target = "username")
    @Mapping(source = "following.email", target = "email")
    FollowResponseDto toFollowResponseDto(Follow follow);


}
