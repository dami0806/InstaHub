//package com.sparta.instahub.domain.auth.mapper;
//
//import com.sparta.instahub.domain.auth.entity.User;
//import com.sparta.instahub.domain.user.dto.UserResponseDto;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Mappings;
//
//@Mapper(componentModel = "spring")
//public interface UserMapper {
//    @Mappings({
//            @Mapping(source = "id", target = "id"),
//            @Mapping(source = "userId", target = "userId"),
//            @Mapping(source = "email", target = "email"),
//            @Mapping(source = "username", target = "username")
//            // 필요한 경우 나머지 필드도 여기에 추가
//    })
//    UserResponseDto toDto(User user);
//
//    @Mappings({
//            @Mapping(source = "id", target = "id"),
//            @Mapping(source = "userId", target = "userId"),
//            @Mapping(source = "email", target = "email"),
//            @Mapping(source = "username", target = "username")
//            // 필요한 경우 나머지 필드도 여기에 추가
//    })
//    User toEntity(UserResponseDto userResponseDto);
//}