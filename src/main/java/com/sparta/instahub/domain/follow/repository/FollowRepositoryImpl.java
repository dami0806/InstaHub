package com.sparta.instahub.domain.follow.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.instahub.domain.auth.entity.QUser;
import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.follow.entity.QFollow;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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

    @Override
    public Page<User> findFollowings(User follower, Pageable pageable) {

        QFollow follow = QFollow.follow;
        QUser user = QUser.user;

        List<User> followings = queryFactory.select(follow.following)
                .from(follow)
                .where(follow.follower.eq(follower))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.selectFrom(follow)
                .where(follow.follower.eq(follower))
                .fetchCount();
        return new PageImpl<>(followings, pageable, total);
    }

    @Override
    public Page<User> findFollowers(User following, Pageable pageable) {

        QFollow follow = QFollow.follow;
        QUser user = QUser.user;

        List<User> followers = queryFactory.select(follow.follower)
                .from(follow)
                .where(follow.following.eq(following))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.selectFrom(follow)

                .where(follow.following.eq(following))
                .fetchCount();
        return new PageImpl<>(followers, pageable, total);
    }
}