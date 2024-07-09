package com.sparta.instahub.domain.follow.dto;

import com.sparta.instahub.domain.follow.entity.Follow;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FollowResponseDto {
     private UUID id;
     private String username;
     private String email;
}
