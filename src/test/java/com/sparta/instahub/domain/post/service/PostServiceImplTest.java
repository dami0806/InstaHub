package com.sparta.instahub.domain.post.service;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.auth.entity.UserRole;
import com.sparta.instahub.domain.auth.entity.UserStatus;
import com.sparta.instahub.domain.auth.service.UserServiceImpl;
import com.sparta.instahub.domain.post.dto.PostResponseDto;
import com.sparta.instahub.domain.post.entity.Post;
import com.sparta.instahub.domain.post.exception.InaccessiblePostException;
import com.sparta.instahub.domain.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("단위 테스트 PostService의 ")
@ExtendWith(MockitoExtension.class)// Mockito확장으로 mock객 초기화 자동화
class PostServiceImplTest {


    // @Mock: 목 객체를 생성
    @Mock
    private PostRepository postRepository;

    @Mock
    private UserServiceImpl userService;

//    @Mock
//    private S3Service s3Service;

    // @InjectMocks: 목 객체를 주입해서 테스트할 객체를 생성
    @InjectMocks
    private PostServiceImpl postService;

    private UUID postId;
    private Post post;
    private User user;


    // @BeforeEach: 테스트 실행 전 초기화 작업
    @BeforeEach
    void setUp() {
        postId = UUID.randomUUID();
        user = User.builder()
                .userId("userID")
                .email("email1")
                .password("password")
                .username("Test User")
                .userStatus(UserStatus.ACTIVE)
                .userRole(UserRole.USER)
                .build();

        post = Post.builder()
                .title("Test Post")
                .content("Test Content")
                .user(user)
                .build();

        postId = post.getId();
    }

    // 모든 게시물 조회 테스트
    @Test
    @DisplayName("모든 게시물 조회")
    void getAllPosts() {
        // 주어진 조건 (given): 리포지토리가 모든 게시물을 반환
        when(postRepository.findAll()).thenReturn(List.of(post));

        // 실행 (when): 서비스의 getAllPosts 메서드를 호출
        List<PostResponseDto> result = postService.getAllPosts();

        // 검증 (then): 결과를 검증하고, 리포지토리 메서드 호출
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Post");
    }


    // ID로 게시물 조회 테스트
    @Test
    @DisplayName("ID로 게시물 조회")
    void getPostById() {
        // 주어진 조건 (given): 리포지토리가 특정 ID의 게시물을 반환
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // 실행 (when): 서비스의 getPostById 메서드를 호출
        Post post = postService.getPostById(postId);

        // 검증 (then): 결과를 검증
        assertThat(post.getId()).isEqualTo(postId);
    }


    // ID로 게시물 조회 예외 테스트
    @Test
    @DisplayName("ID로게시물 조회 예외")
    void getPostByIdException() {

        // 주어진 조건 (given): 리포지토리에 없는 ID
        postId = UUID.randomUUID();
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // 실행 (when): 예외가 발생하는지 확인
        InaccessiblePostException exception = assertThrows(InaccessiblePostException.class, () -> {
            postService.getPostById(postId);
        });

        // 검증 (then): 예외 메시지를 확인
        assertEquals("post ID가 잘못된 ID입니다.", exception.getMessage());

    }

    // 새 게시물 생성 테스트
    @Test
    @DisplayName("새 게시물 생성")
    void createPost() {
        // 주어진 조건 (given): 사용자와 이미지 업로드, 게시물 저장을 설정

        // 주어진 조건 (given): 사용자와 이미지 업로드, 게시물 저장을 설정
        MultipartFile image = mock(MultipartFile.class);
        when(image.isEmpty()).thenReturn(true);
        when(userService.getUserByName("Test User")).thenReturn(user);

        // 실행 (when): 서비스의 createPost 메서드를 호출
        Post createdPost = postService.createPost("Test Title", "Test Content", image, "Test User");

        // 검증 (then): 결과를 검증하고, 리포지토리 메서드 호출을 확인
        assertThat(createdPost.getTitle()).isEqualTo("Test Title");
        assertThat(createdPost.getContent()).isEqualTo("Test Content");
        assertThat(createdPost.getUser().getUsername()).isEqualTo("Test User");
    }
        // 게시물 수정 테스트
    @Test
    @DisplayName("게시물 수정 테스트")
    void updatePost() {

        // 주어진 조건 (given): 사용자와 게시물 조회, 이미지 업로드, 게시물 저장을 설정
        // 실행 (when): 서비스의 updatePost 메서드를 호출
        // 검증 (then): 결과를 검증하고, 리포지토리 메서드 호출을 확인
    }




// 게시물 삭제 테스트
// 주어진 조건 (given): 사용자와 게시물 조회를 설정
// 실행 (when): 서비스의 deletePost 메서드를 호출
// 검증 (then): 리포지토리 메서드 호출을 확인

// 모든 게시물 삭제 테스트
// 주어진 조건 (given): 리포지토리가 모든 게시물을 반환하도록 설정
// 실행 (when): 서비스의 deleteAllPosts 메서드를 호출

// 검증 (then): 리포지토리와 S3 서비스 메서드 호출을 확인

}