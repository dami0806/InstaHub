package com.sparta.instahub.domain.follow.dto;

import com.sparta.instahub.domain.follow.entity.Follow;
import lombok.*;

import java.util.UUID;

@Getter
public class FollowResponseDto {
    final private UUID id;
    final private String username;
    final private String email;
    public FollowResponseDto(Follow follow) {
        this.id = follow.getId();
        this.username = follow.getFollowing().getUsername();
        this.email = follow.getFollowing().getEmail();
    }
}
