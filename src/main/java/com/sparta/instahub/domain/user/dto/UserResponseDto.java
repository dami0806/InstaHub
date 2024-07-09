package com.sparta.instahub.domain.user.dto;

import com.sparta.instahub.domain.auth.entity.User;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private UUID id;
    private String userId;
    private String email;
    private String username;


    public void updateFollowerCount(Long followerCount) {
        this.username = this.username + " (" + followerCount + ")";
    }
}
