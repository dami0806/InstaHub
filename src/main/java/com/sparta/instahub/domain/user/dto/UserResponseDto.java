package com.sparta.instahub.domain.user.dto;

import com.sparta.instahub.domain.auth.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserResponseDto {
    private UUID id;
    private String userId;
    private String email;
    private String username;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }

    public User toEntity() {
        return User.builder()
                .id(this.id)
                .userId(this.userId)
                .email(this.email)
                .username(this.username)
                .build();
    }

    public void updateFollowerCount(Long followerCount) {
        this.username = this.username + " (" + followerCount + ")";
    }
}
