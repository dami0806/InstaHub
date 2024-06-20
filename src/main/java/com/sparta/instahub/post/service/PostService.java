package com.sparta.instahub.post.service;

import com.sparta.instahub.auth.entity.User;
import com.sparta.instahub.post.entity.Post;
import com.sparta.instahub.post.repository.PostRepository;
import com.sparta.instahub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

// Post 엔티티에 대한 비즈니스 로직을 처리하는 서비스 클래스
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    // 모든 게시물 조회
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // ID로 게시물 조회
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
    }

    // 새 게시물 생성
    public Post createPost(String title, String content, String imageUrl) {
        User user = getCurrentUser(); // 현재 로그인된 사용자 가져오기
        Post post = Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .build();
        return postRepository.save(post); // Post 객체 저장
    }

    // 게시물 수정
    public Post updatePost(Long id, String title, String content, String imageUrl) {
        Post post = getPostById(id); // ID로 게시물 조회
        post.update(title, content, imageUrl); // 게시물 업데이트
        return postRepository.save(post); // 업데이트된 게시물 저장
    }

    // 게시물 삭제
    public void deletePost(Long id) {
        postRepository.deleteById(id); // ID로 게시물 삭제
    }

    // 현재 로그인된 사용자 가져오기
    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}