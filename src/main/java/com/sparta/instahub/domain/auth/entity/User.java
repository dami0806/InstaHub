package com.sparta.instahub.domain.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.f4b6a3.ulid.UlidCreator;
import com.sparta.instahub.domain.comment.entity.Comment;
import com.sparta.instahub.common.entity.BaseEntity;
import com.sparta.instahub.domain.follow.entity.Follow;
import com.sparta.instahub.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties({"posts", "comments", "profile"})
public class User extends BaseEntity {

    // 기본키
    @Id
    @Column(columnDefinition = "BINARY(16)")
    @GeneratedValue(generator = "uuid2")
    private UUID id = UlidCreator.getMonotonicUlid().toUuid();

    // 사용자 ID
    @Column(nullable = false, unique = true)
    private String userId;

    // 이메일
    @Column(nullable = false, unique = true)
    private String email;

    // 비밀번호
    @Column(nullable = false)
    private String password;

    // 이름
    @Column(nullable = false)
    private String username;

    // 사용자 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus;

    // 사용자 역할
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;


    // refreshToken
    @Column
    private String refreshToken;

    // User와 Post는 일대다 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Post> posts; // 사용자가 작성한 게시물 목록

    // User와 Comment는 일대다 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments; // 사용자가 작성한 댓글 목록

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Follow> following;

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Follow> followers;

    public User(String name, String email, String password) {
        this.username = name;
        this.email = email;
        this.password = password;
    }
//    // User와 PasswordHistroy는 1대다 관계
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<PasswordHistory> passwordHistories; // 사용자가 작성한 비밀번호 목록


    // 사용자 역할 및 상태를 업데이트
    public void promoteToAdmin() {
        this.userRole = UserRole.ADMIN;
    }

    // 차단하기
    public void blockUser() {
        this.userStatus = UserStatus.BLOCKED;
    }

    // 차단 풀기
    public void unblockUser() {
        this.userStatus = UserStatus.ACTIVE;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateUserId(String userId) {
        this.userId = userId;
    }

    // 비밀번호 업데이트
    public void updatePassword(String password){
        this.password = password;
    }

    // 로그인 상태 변경
    public void login() {
        this.userStatus = UserStatus.ACTIVE;
    }

    // 로그아웃 (UserStatus 변경)
    public void logout() {
        this.userStatus = UserStatus.LOGOUT; // userStatus를 LOGOUT으로 변경
    }

    // 탈퇴 (UserStatus 변경)
    public void withdraw() {
        this.userStatus = UserStatus.WITHDRAWN;
    }

    // 리프레시 토큰 업데이트
    public void updateRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateUserRole(final UserRole userRole) {
        this.userRole = userRole;
    }

    public void updateUserStatus(final UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    // 토큰 삭제
    public void clearRefreshToken() {
        this.refreshToken = null;
    }


}