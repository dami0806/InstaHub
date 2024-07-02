package com.sparta.instahub.domain.auth.entity;

public enum UserStatus {
    ACTIVE,
    BLOCKED, // 차단
    LOGOUT, // 로그아웃
    WITHDRAWN // 탈퇴
}