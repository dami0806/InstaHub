//package com.sparta.instahub.domain.post.mapper;
//
//import com.sparta.instahub.domain.post.dto.PostResponseDto;
//import com.sparta.instahub.domain.post.entity.Post;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingConstants;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//
//import java.util.List;
//import java.util.stream.Collectors;
//@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
//public interface PostMapper {
//
//    @Mapping(target = "username", source = "user.username") // 엔티티의 User 객체에서 username 필드를 매핑
//    @Mapping(target = "comments", ignore = true) // comments는 별도의 로직에서 처리하도록 무시
//    PostResponseDto toPostResponseDto(Post post);
//
//    List<PostResponseDto> toPostResponseDtoList(List<Post> posts);
//}
