package com.sparta.instahub.domain.auth.service;

import com.sparta.instahub.domain.auth.dto.SignupRequestDto;
import com.sparta.instahub.domain.auth.dto.TokenResponseDto;
import com.sparta.instahub.domain.auth.entity.LoginRequest;
import com.sparta.instahub.domain.auth.entity.LoginResponse;
import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.auth.entity.UserRole;
import com.sparta.instahub.domain.auth.entity.UserStatus;
import com.sparta.instahub.domain.profile.dto.PasswordRequestDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    // 사용자 정보 업데이트
    User update(UUID userId, String newEmail, String newUserId);

    // 회원가입
    void signup(SignupRequestDto signupRequest);

    // 로그인
    LoginResponse login(LoginRequest loginRequest);

    // 리프레시 토큰
    TokenResponseDto refresh(String refreshToken);

    // 로그아웃
    void logout(String userId, String accessToken);

    // 탈퇴
    void withdraw(String userId, String password, String accessToken, String refreshToken);

    User getCurrentAdmin();

    List<User> getAllUsers();

    User getUserById(UUID id);

    User updateUser(UUID id, String username, String email, UserRole userRole, UserStatus userStatus);

    void deleteUser(UUID id);

    User promoteUserToAdmin(UUID id);

    User blockUser(UUID id);

    User unblockUser(UUID id);

    User getUserByName(String username);

    User getUserByNameActive(String username);
}
