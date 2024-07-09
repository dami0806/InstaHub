package com.sparta.instahub.domain.follow.service;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.auth.service.UserService;
import com.sparta.instahub.domain.follow.entity.Follow;
import com.sparta.instahub.domain.follow.mapper.FollowMapper;
import com.sparta.instahub.domain.follow.repository.FollowRepository;
import com.sparta.instahub.domain.post.repository.PostRepository;
import com.sparta.instahub.domain.user.dto.UserResponseDto;
import com.sparta.instahub.domain.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;
    private final UserService userService;
    private final UserMapper userMapper;


    // 팔로우 하기
    @Override
    public void followUser(UUID followerId, UUID followingId) {
        User follower = userService.getUserById(followerId);
        User following = userService.getUserById(followingId);

        validateFollowAction(follower, following);

        Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .build();
        followRepository.save(follow);
    }

    // 팔로우 취소하기
    @Override
    public void unfollowUser(UUID followerId, UUID followingId) {
        User follower = userService.getUserById(followerId);
        User following = userService.getUserById(followingId);

        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 관계가 존재하지 않습니다."));

        followRepository.delete(follow);
    }

    // 팔로우 한 사람들 보기
    @Override
    public Page<UserResponseDto> getFollowings(UUID userId, Pageable pageable) {
        User user = getCurrentUser(userId);
        Page<User> followings = followRepository.findFollowings(user, pageable);
        List<UserResponseDto> followingsDto = followings.stream()
               // .map(UserResponseDto::new)
                .map(userMapper::toUserResponseDto)
                .collect(Collectors.toList());
        return new PageImpl<>(followingsDto, pageable, followingsDto.size());
    }

    // 나를 팔로우한 팔로워 보기
    @Override
    public Page<UserResponseDto> getFollowers(UUID userId, Pageable pageable) {
        User user = userService.getUserById(userId);

        Page<User> followers = followRepository.findFollowers(user, pageable);
//        List<UserResponseDto> followersDto = followers.stream()
//                .map(userMapper.toUserResponseDto() )
//                .collect(Collectors.toList());
        List<UserResponseDto> followersDto = followers.stream()
                .map(userMapper::toUserResponseDto)
                .collect(Collectors.toList());
        return new PageImpl<>(followersDto, pageable, followersDto.size());
    }


    // QueryDSL을 사용하여 팔로워 수 상위 10명의 사용자 가져오기
    @Override
    public List<UserResponseDto> getTop10UserByFollowersCount() {
        List<User> top10Users = followRepository.findTop10ByFollowerCount();
        return top10Users.stream()
                .map(userMapper::toUserResponseDto)
                .collect(Collectors.toList());
    }

    // 팔로워들의 게시물 조회
    private void validateFollowAction(User follower, User following) {
        if (follower.equals(following)) {
            throw new IllegalArgumentException("스스로를 팔로우할수는 없습니다.");
        }

        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new IllegalArgumentException("이미 팔로우 중입니다.");
        }
    }

    private User getCurrentUser(UUID userId) {
        return userService.getUserById(userId);
    }
}
