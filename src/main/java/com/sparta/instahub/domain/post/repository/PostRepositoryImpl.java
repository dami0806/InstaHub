package com.sparta.instahub.domain.post.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.instahub.domain.auth.entity.QUser;
import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.post.entity.Post;
import com.sparta.instahub.domain.post.entity.QPost;
import com.sparta.instahub.domain.post.entity.SearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor

public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Post> findAllBySearchCond( SearchCond searchCond, Pageable pageable) {
        QPost post = QPost.post;

        BooleanBuilder builder = new BooleanBuilder();
        if (searchCond.getUsername() != null) {
            builder.and(post.user.username.containsIgnoreCase(searchCond.getUsername()));
        }

        List<Post> posts = queryFactory.selectFrom(post)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(post)
                .where(builder)
                .fetchCount();

        return new PageImpl<>(posts, pageable, total);
    }
}
