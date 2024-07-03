package com.sparta.instahub.domain.post.service;

import com.sparta.instahub.domain.auth.service.UserServiceImpl;
import com.sparta.instahub.domain.post.entity.Post;
import com.sparta.instahub.domain.post.repository.PostRepository;
import com.sparta.instahub.s3.service.S3Service;
import org.springframework.security.core.userdetails.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("단위 테스트 PostService의 ")
@ExtendWith(MockitoExtension.class)// Mockito확장으로 mock객 초기화 자동화
class PostServiceImplTest {


    // @Mock: 목 객체를 생성
    @Mock
    private PostRepository postRepository;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private S3Service s3Service;

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
    user = User.builder().

    post = Post.builder()
            .user(user)
            .title("테스트 제목")
            .content("테스트 내용")
            .imageUrl("test.png")
            .
}

// 모든 게시물 조회 테스트

// 주어진 조건 (given): 리포지토리가 모든 게시물을 반환하도록 설정합니다.
// 실행 (when): 서비스의 getAllPosts 메서드를 호출합니다.
// 검증 (then): 결과를 검증하고, 리포지토리 메서드 호출을 확인합니다.

// ID로 게시물 조회 테스트
// 주어진 조건 (given): 리포지토리가 특정 ID의 게시물을 반환하도록 설정합니다.
// 실행 (when): 서비스의 getPostById 메서드를 호출합니다.
// 검증 (then): 결과를 검증하고, 리포지토리 메서드 호출을 확인합니다.

// ID로 게시물 조회 예외 테스트
// 주어진 조건 (given): 리포지토리가 특정 ID의 게시물을 찾지 못하도록 설정합니다.
// 실행 (when): 예외가 발생하는지 확인합니다.
// 검증 (then): 예외 메시지를 확인하고, 리포지토리 메서드 호출을 확인합니다.

// 새 게시물 생성 테스트
// 주어진 조건 (given): 사용자와 이미지 업로드, 게시물 저장을 설정합니다.
// 실행 (when): 서비스의 createPost 메서드를 호출합니다.
// 검증 (then): 결과를 검증하고, 리포지토리 메서드 호출을 확인합니다.

// 게시물 수정 테스트
// 주어진 조건 (given): 사용자와 게시물 조회, 이미지 업로드, 게시물 저장을 설정합니다.
// 실행 (when): 서비스의 updatePost 메서드를 호출합니다.
// 검증 (then): 결과를 검증하고, 리포지토리 메서드 호출을 확인합니다.

// 게시물 삭제 테스트
// 주어진 조건 (given): 사용자와 게시물 조회를 설정합니다.
// 실행 (when): 서비스의 deletePost 메서드를 호출합니다.
// 검증 (then): 리포지토리 메서드 호출을 확인합니다.

// 모든 게시물 삭제 테스트
// 주어진 조건 (given): 리포지토리가 모든 게시물을 반환하도록 설정합니다.
// 실행 (when): 서비스의 deleteAllPosts 메서드를 호출합니다.
// 검증 (then): 리포지토리와 S3 서비스 메서드 호출을 확인합니다.

}