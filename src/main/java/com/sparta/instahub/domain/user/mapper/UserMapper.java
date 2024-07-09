package com.sparta.instahub.domain.user.mapper;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.user.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    UserResponseDto toUserResponseDto(User user);

//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "userId", target = "userId")
//    @Mapping(source = "email", target = "email")
//    @Mapping(source = "username", target = "username")
//    User toUserEntity(UserResponseDto userResponseDto);
}
