package com.sparta.instahub.domain.follow.controller;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.auth.service.UserService;
import com.sparta.instahub.domain.follow.service.FollowService;
import com.sparta.instahub.domain.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follows")
public class FollowController {

    private final FollowService followService;
    private final UserService userService;

    // 팔로우 하기
    @PostMapping("/{followingId}")
    public ResponseEntity<Void> followUser(@PathVariable UUID followingId,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        User follower = getCurrentUserId(userDetails);

        followService.followUser(follower.getId(), followingId);
        return ResponseEntity.ok().build();
    }

    // 팔로우 취소하기
    @DeleteMapping("/{followingId}")
    public ResponseEntity<Void> unfollowUser(@PathVariable UUID followingId,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        User follower = getCurrentUserId(userDetails);

        followService.unfollowUser(follower.getId(), followingId);
        return ResponseEntity.ok().build();
    }

    // 팔로우 한 사람들 보기
    @GetMapping("/followings")
    public ResponseEntity<Page<UserResponseDto>> getFollowing(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        User follower = getCurrentUserId(userDetails);
        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponseDto> following = followService.getFollowings(follower.getId(),pageable);
        return ResponseEntity.ok(following);
    }

    // 나를 팔로우 한 사람들 보기
    @GetMapping("/followers")
    public ResponseEntity<Page<UserResponseDto>> getFollowers(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        User follower = getCurrentUserId(userDetails);
        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponseDto> followers = followService.getFollowers(follower.getId(),pageable);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/top10")
    public ResponseEntity<List<UserResponseDto>> getTop10UserByFollowersCount() {
        List<UserResponseDto> top10User = followService.getTop10UserByFollowersCount();
        return ResponseEntity.ok(top10User);
    }
    private User getCurrentUserId(UserDetails userDetails) {
        return userService.getUserByName(userDetails.getUsername());
    }
}
