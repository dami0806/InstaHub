package com.sparta.instahub.domain.follow.service;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.auth.service.UserService;
import com.sparta.instahub.domain.follow.entity.Follow;
import com.sparta.instahub.domain.follow.repository.FollowRepository;
import com.sparta.instahub.domain.post.dto.PostResponseDto;
import com.sparta.instahub.domain.post.entity.Post;
import com.sparta.instahub.domain.post.entity.SearchCond;
import com.sparta.instahub.domain.post.repository.PostRepository;
import com.sparta.instahub.domain.user.dto.UserResponseDto;
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
    private final PostRepository postRepository;

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
        List<UserResponseDto> followings = followRepository.findByFollowing(user).stream()
                .map(follow -> new UserResponseDto(follow.getFollower()))
                .collect(Collectors.toList());
        return new PageImpl<>(followings, pageable, followings.size());
//        return followRepository.findByFollower(user).stream()
//                .map(Follow::getFollowing)
//                .collect(Collectors.toList());
    }

    // 나를 파로우한 사람들 보기
    @Override
    public Page<UserResponseDto> getFollowers(UUID userId, Pageable pageable) {
        User user = userService.getUserById(userId);
        List<UserResponseDto> followers = followRepository.findByFollower(user).stream()
                .map(follow -> new UserResponseDto(follow.getFollowing()))

                .collect(Collectors.toList());
        return new PageImpl<>(followers, pageable, followers.size());
    }

    @Override
    public Page<PostResponseDto> getFollowerPosts(UUID userId, SearchCond searchCond, Pageable pageable) {
        User user = userService.getUserById(userId);
        List<User> followings = followRepository.findByFollower(user).stream()
                .map(Follow::getFollowing)
                .collect(Collectors.toList());

        Page<Post> posts;
        if (searchCond.getUsername() != null && !searchCond.getUsername().isEmpty()) {
            posts = postRepository.findByUserInAndUserUsernameContainingIgnoreCase(followings, searchCond.getUsername(), pageable);
        } else {
            posts = postRepository.findByUserIn(followings, pageable);
        }

        List<PostResponseDto> postResponseDtos = posts.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

        return new PageImpl<>(postResponseDtos, pageable, posts.getTotalElements());
    }


    @Override
    public List<UserResponseDto> getTop10UserByFollowersCount() {
        List<Object[]> results = followRepository.findTop10ByFollowerCount();

        return results.stream()
                .map(result -> {
                    User user = (User) result[0];
                    Long followerCount = (Long) result[1];
                    UserResponseDto userResponseDto = new UserResponseDto(user);
                    userResponseDto.updateFollowerCount(followerCount);
                    return userResponseDto;
                }).collect(Collectors.toList());
    }

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
