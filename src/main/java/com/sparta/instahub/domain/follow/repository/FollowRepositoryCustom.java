package com.sparta.instahub.domain.follow.repository;


import com.sparta.instahub.domain.auth.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FollowRepositoryCustom {
    List<User> findTop10ByFollowerCount();

    Page<User> findFollowings(User follower, Pageable pageable);
    Page<User> findFollowers(User following, Pageable pageable);
}
