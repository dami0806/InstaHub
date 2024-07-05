package com.sparta.instahub.domain.follow.repository;


import com.sparta.instahub.domain.auth.entity.User;

import java.util.List;

public interface FollowRepositoryCustom {
    List<User> findTop10ByFollowerCount();
}
