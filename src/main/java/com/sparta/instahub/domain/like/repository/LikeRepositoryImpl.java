package com.sparta.instahub.domain.like.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.comment.entity.Comment;
import com.sparta.instahub.domain.comment.entity.QComment;
import com.sparta.instahub.domain.like.entity.Like;
import com.sparta.instahub.domain.like.entity.QLike;
import com.sparta.instahub.domain.post.entity.Post;
import com.sparta.instahub.domain.post.entity.QPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;
}
