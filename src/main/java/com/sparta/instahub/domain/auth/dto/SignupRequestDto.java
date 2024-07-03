package com.sparta.instahub.domain.auth.dto;

import com.sparta.instahub.domain.auth.entity.UserRole;
import com.sparta.instahub.domain.auth.entity.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SignupRequestDto {
    private String userId;        // 사용자 ID
    private String email;         // 이메일
    private String password;      // 비밀번호
    private String username;      // 이름
    private UserRole userRole;    // 사용자 역할
}

