package com.sparta.instahub.domain.follow.service;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.post.dto.PostResponseDto;
import com.sparta.instahub.domain.post.entity.SearchCond;
import com.sparta.instahub.domain.user.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface FollowService {

    void followUser(UUID followerId, UUID followingId);

    void unfollowUser(UUID followerId, UUID followingId);

    Page<UserResponseDto> getFollowings(UUID userId, Pageable pageable);
    Page<UserResponseDto> getFollowers(UUID userId, Pageable pageable);

    // 필터링한 팔로워 게시물
    Page<PostResponseDto> getFollowerPosts(UUID userId, SearchCond searchCond, Pageable pageable);


    List<UserResponseDto> getTop10UserByFollowersCount();
}
