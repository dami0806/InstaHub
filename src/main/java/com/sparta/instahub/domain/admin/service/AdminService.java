package com.sparta.instahub.domain.admin.service;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.auth.entity.UserRole;
import com.sparta.instahub.domain.auth.entity.UserStatus;
import com.sparta.instahub.domain.post.dto.PostResponseDto;
import com.sparta.instahub.domain.post.entity.Post;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


public interface AdminService {

    void checkIfAdmin(User user);

    // 전체 회원 조회
    List<User> getAllUsers();

    // ID로 회원 조회
    @Transactional(readOnly = true)
    public User getUserById(UUID id);

    // 회원 정보 수정
    User updateUser(UUID id, String username, String email, UserRole userRole, UserStatus userStatus);

    // 회원 삭제
    void deleteUser(UUID id);

    // 회원 운영진으로 승격
    @Transactional
    public User promoteUserToAdmin(UUID id);

    // 회원 차단
    User blockUser(UUID id);

    // 회원 차단 해제
    User unblockUser(UUID id);

    // 공지글 등록
    PostResponseDto createAnnouncement(String title, String content, MultipartFile imageUrl);

    // 모든 게시글 삭제
    void deleteAllPosts();

    // 특정 게시글 삭제 (관리자)
    void deletePost(UUID postId);

    // 현재 로그인된 관리자 가져오기
    User getCurrentAdmin();
}
