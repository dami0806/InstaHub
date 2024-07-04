package com.sparta.instahub.domain.post.service;

import com.sparta.instahub.domain.post.dto.PostResponseDto;
import com.sparta.instahub.domain.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

// Post 엔티티에 대한 비즈니스 로직을 처리하는 서비스 클래스
public interface PostService {

    List<PostResponseDto> getAllPosts(int page, int size, String sortBy);
    // ID로 게시물 조회
    PostResponseDto getPostById(UUID id);

    // 새 게시물 생성
    PostResponseDto createPost(String title, String content, MultipartFile imageUrl, String username);

    // 게시물 수정
    PostResponseDto updatePost(UUID id, String title, String content, MultipartFile imageUrl, String username);

    // 게시물 삭제
    void deletePost(UUID id, String username);

    // 모든 게시물 삭제
    void deleteAllPosts();

    Post getPost(UUID postId);
}
