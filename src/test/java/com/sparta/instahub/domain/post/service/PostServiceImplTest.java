package com.sparta.instahub.domain.post.service;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.auth.entity.UserRole;
import com.sparta.instahub.domain.auth.entity.UserStatus;
import com.sparta.instahub.domain.auth.exception.UnauthorizedException;
import com.sparta.instahub.domain.auth.service.UserService;
import com.sparta.instahub.domain.auth.service.UserServiceImpl;
import com.sparta.instahub.domain.post.dto.PostResponseDto;
import com.sparta.instahub.domain.post.entity.Post;
import com.sparta.instahub.domain.post.exception.InaccessiblePostException;
import com.sparta.instahub.domain.post.repository.PostRepository;
import com.sparta.instahub.s3.service.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("단위 테스트 PostService의 ")
@ExtendWith(MockitoExtension.class)// Mockito확장으로 mock객 초기화 자동화
class PostServiceImplTest {


    // @Mock: 목 객체를 생성
    @Mock
    private PostRepository postRepository;

    @Mock
    private UserService userService;

    @Mock
    private S3Service s3Service;

    // @InjectMocks: 목 객체를 주입해서 테스트할 객체를 생성
    @InjectMocks
    private PostServiceImpl postService;

    private MultipartFile image;

    private UUID postId;
    private Post post;
    private User user;


    // @BeforeEach: 테스트 실행 전 초기화 작업
    @BeforeEach
    void setUp() {

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
                .imageUrl("image.jpg")
                .user(user)
                .build();


        image = new MockMultipartFile(
                "image", // MultipartFile의 이름
                "test.jpg", // 원래 파일 이름
                "image/jpeg", // 파일의 MIME 타입
                new byte[]{1, 2, 3} // 파일의 내용 (바이트 배열)
        );
    }

    // 모든 게시물 조회 테스트
    @Test
    @DisplayName("모든 게시물 조회")
    void getAllPosts() {
        // 주어진 조건 (given): 리포지토리가 모든 게시물을 반환
        Pageable pageable = PageRequest.of(0, 3, Sort.Direction.DESC, "createAt");
        Page<Post> postPage = new PageImpl<>(List.of(post), pageable, 1);

        when(postRepository.findAll(pageable)).thenReturn(postPage);

        // 실행 (when): 서비스의 getAllPosts 메서드를 호출
        List<PostResponseDto> result = postService.getAllPosts(0, 3, "createAt");

        // 검증 (then): 결과를 검증하고, 리포지토리 메서드 호출
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Post");
    }

    @Test
    @DisplayName("ID로 게시물 조회")
    void getPostById() {
        // 주어진 조건 (given): 리포지토리가 특정 ID의 게시물을 반환
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // 실행 (when): 서비스의 getPostById 메서드를 호출
        PostResponseDto postResponse = postService.getPostById(postId);

        // 검증 (then): 결과를 검증
        assertThat(postResponse.getId()).isEqualTo(post.getId());
    }


    // ID로 게시물 조회 예외 테스트
    @Test
    @DisplayName("ID로 게시물 조회 예외")
    void getPostByIdException() {

        // 주어진 조건 (given): 리포지토리에 없는 ID
        // postId = UUID.randomUUID();
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
        //user
        when(userService.getUserByNameActive("Test User")).thenReturn(user);

        // s3 upload
        when(s3Service.uploadFile(any(MultipartFile.class))).thenReturn("image.jpg");

        //repository에 save
        when(postRepository.save(any(Post.class))).thenReturn(post);

        // 실행 (when): 서비스의 createPost 메서드를 호출
        PostResponseDto createdPost = postService.createPost("Test Title", "Test Content", image, "Test User");

        // 검증 (then): 결과를 검증하고, 리포지토리 메서드 호출을 확인
        assertThat(createdPost.getTitle()).isEqualTo("Test Title");
        assertThat(createdPost.getContent()).isEqualTo("Test Content");
        assertThat(createdPost.getImageUrl()).isEqualTo("image.jpg");

        // 서비스 호출 검증
        verify(userService, times(1)).getUserByNameActive("Test User");
        verify(s3Service, times(1)).uploadFile(any(MultipartFile.class));
        verify(postRepository, times(1)).save(any(Post.class));

    }

    // 게시물 수정 테스트
    @Test
    @DisplayName("게시물 수정 테스트")
    void updatePost() {

        // 주어진 조건 (given): 사용자와 게시물 조회, 이미지 업로드, 게시물 저장을 설정
        MultipartFile newImage = new MockMultipartFile(
                "image",
                "new.jpg",
                "image/jpeg",
                new byte[]{4, 5, 6}
        );

        //1. 사용자와 작성자가 일치하는지 확인
        when(userService.getUserByNameActive("Test User")).thenReturn(user);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(s3Service.uploadFile(newImage)).thenReturn("new.jpg");

        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostResponseDto updatePost = postService.updatePost(postId, "New Title", "New Content", newImage, "Test User");
        // 실행 (when): 서비스의 updatePost 메서드를 호출
        //1. 게시물과 이미지 수정
        // 검증 (then): 결과를 검증하고, 리포지토리 메서드 호출을 확인
        assertThat(updatePost.getTitle()).isEqualTo("New Title");
        assertThat(updatePost.getContent()).isEqualTo("New Content");
        assertThat(updatePost.getImageUrl()).isEqualTo("new.jpg");
    }

    // 작성자와 user가 다른 경우 예외
    @Test
    @DisplayName("게시물 수정,삭제 실패 - 작성자가 아님")
    void updatePostException() {
        // 주어진 조건 (given): 사용자를 한명 등록
        User anotherUser = User.builder()
                .userId("userID2")
                .email("email2")
                .password("password2")
                .username("another User")
                .userStatus(UserStatus.ACTIVE)
                .userRole(UserRole.USER)
                .build();

        MultipartFile newImage = new MockMultipartFile(
                "image",
                "new.jpg",
                "image/jpeg",
                new byte[]{4, 5, 6}
        );

        when(userService.getUserByNameActive("another User")).thenReturn(anotherUser);

        // 실행 (when):
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // 검증 (then): update할때 예외 보기
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            postService.updatePost(postId, "New Title", "New Content", newImage, "another User");
        });

        assertEquals("해당 권한이 없는 사용자 입니다", exception.getMessage());
    }

    // 게시물 삭제 테스트
    @Test
    @DisplayName("게시물 삭제 테스트")
    void DeletePost() {
        // 주어진 조건 (given):
        when(userService.getUserByNameActive("Test User")).thenReturn(user);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // 실행 (when):
        postService.deletePost(postId, user.getUsername());

        // 검증 (then):
        verify(userService, times(1)).getUserByNameActive("Test User");
        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, times(1)).deleteById(postId);
    }

    @Test
    @DisplayName("게시물 삭제 실패 - 작성자가 아님")
    void deletePostException() {
        // 주어진 조건 (given):
        User anotherUser = User.builder()
                .userId("userID2")
                .email("email2")
                .password("password2")
                .username("another User")
                .userStatus(UserStatus.ACTIVE)
                .userRole(UserRole.USER)
                .build();

        // 실행 (when):
        when(userService.getUserByNameActive("another User")).thenReturn(anotherUser);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // 검증 (then): update할때 예외 보기
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            postService.deletePost(postId, "another User");
        });

        assertEquals("해당 권한이 없는 사용자 입니다", exception.getMessage());
    }
}
