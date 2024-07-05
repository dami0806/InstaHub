package com.sparta.instahub.domain.follow.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.instahub.domain.auth.entity.QUser;
import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.follow.entity.QFollow;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // QueryDSL을 사용하여 팔로워 수 상위 10명 조회
    @Override
    public List<User> findTop10ByFollowerCount() {
        QFollow follow = QFollow.follow;
        QUser user = QUser.user;

        return queryFactory.select(follow.following)
                .from(follow)
                .groupBy(follow.following)
                .orderBy(follow.following.count().desc())
                .limit(10)
                .fetch();
    }
}