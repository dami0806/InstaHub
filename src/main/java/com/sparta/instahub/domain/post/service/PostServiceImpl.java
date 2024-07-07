package com.sparta.instahub.domain.post.service;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.auth.entity.UserRole;
import com.sparta.instahub.domain.auth.exception.UnauthorizedException;
import com.sparta.instahub.domain.auth.service.UserService;
import com.sparta.instahub.domain.auth.service.UserServiceImpl;
import com.sparta.instahub.domain.follow.service.FollowService;
import com.sparta.instahub.domain.post.dto.PostResponseDto;
import com.sparta.instahub.domain.post.entity.Post;
import com.sparta.instahub.domain.post.entity.SearchCond;
import com.sparta.instahub.domain.post.repository.PostRepository;
import com.sparta.instahub.domain.post.exception.InaccessiblePostException;
import com.sparta.instahub.domain.user.dto.UserResponseDto;
import com.sparta.instahub.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

// Post 엔티티에 대한 비즈니스 로직을 처리하는 서비스 클래스
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final FollowService followService;
    private final S3Service s3Service;

    // 모든 게시물 조회
    @Override
    public List<PostResponseDto> getAllPosts(int page, int size, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> postsPage = postRepository.findAll(pageable);
        //List<Post> posts = postRepository.findAll();
        return postsPage.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    // ID로 게시물 조회
    @Override
    @Transactional(readOnly = true)
    public PostResponseDto getPostById(UUID id) {
        Post post = getPost(id);
        return new PostResponseDto(post);

    }

    // 새 게시물 생성
    @Override
    @Transactional
    public PostResponseDto createPost(String title,
                                      String content,
                                      MultipartFile image,
                                      String username) {
        try {
            User user = getCurrentUser(username);

            String imageUrl = uploadImage(image);

            Post post = Post.builder()
                    .user(user)
                    .title(title)
                    .content(content)
                    .imageUrl(imageUrl)
                    .build();
            postRepository.save(post); // Post 객체 저장
            return new PostResponseDto(post);

        } catch (InaccessiblePostException e) {
            throw new InaccessiblePostException("포스트를 생성할 수 없습니다.");
        }
    }

    // 게시물 수정
    @Override
    @Transactional
    public PostResponseDto updatePost(UUID id, String title, String content, MultipartFile image, String username) {
        try {
            // 현재 로그인된 사용자 가져오기
            User currentUser = getCurrentUser(username);
            Post post = getPost(id); // ID로 게시물 조회

            validateUserPermission(post, currentUser);

            if (image != null && !image.isEmpty()) {
                deleteImage(post.getImageUrl());
                String imageUrl = s3Service.uploadFile(image);
                post.update(title, content, imageUrl);
            } else {
                post.update(title, content, post.getImageUrl());
            }
            postRepository.save(post);
            return new PostResponseDto(post);

        } catch (InaccessiblePostException e) {
            throw new InaccessiblePostException("포스트를 수정할 수 없습니다.");
        }
    }

    // 팔로잉 하는 사용자 게시물 보기
    @Override
    public Page<PostResponseDto> getFollowerPosts(UUID userId, SearchCond searchCond, Pageable pageable) {
        Page<Post> posts = postRepository.findAllBySearchCond(searchCond, pageable);
        return posts.map(PostResponseDto::new);
    }

    // 게시물 삭제
    @Override
    @Transactional
    public void deletePost(UUID id, String username) {
        User currentUser = getCurrentUser(username);
        Post post = getPost(id); // ID로 게시물 조회

        validateUserPermission(post, currentUser);

        deleteImage(post.getImageUrl());
        postRepository.deleteById(id); // ID로 게시물 삭제
    }

    // 모든 게시물 삭제
    @Override
    @Transactional
    public void deleteAllPosts() {
        List<Post> posts = postRepository.findAll();
        posts.forEach(post -> {
            deleteImage(post.getImageUrl());
        });
        postRepository.deleteAll();
    }

    // 이미지 저장하기
    private String uploadImage(MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            return s3Service.uploadFile(image);
        }
        return null;
    }

    // 이미지 삭제하기
    private void deleteImage(String imageUrl) {
        if (imageUrl != null) {
            s3Service.deleteFile(imageUrl);
        }
    }

    // id로 post 불러오기
    public Post getPost(UUID id) {
        return postRepository.findById(id).orElseThrow(() ->
                new InaccessiblePostException("post ID가 잘못된 ID입니다."));
    }

    // / 현재 로그인된 사용자 가져오기
    private User getCurrentUser(String username) {
        return userService.getUserByNameActive(username);
    }

    // 권한이 있는지 확인
    private void validateUserPermission(Post post, User currentUser) {
        if (!post.getUser().equals(currentUser) && currentUser.getUserRole() != UserRole.ADMIN) {
            throw new UnauthorizedException("해당 권한이 없는 사용자 입니다");
        }
    }
}
