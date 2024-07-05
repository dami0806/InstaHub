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

    @Override
    public Page<Post> findLikedPosts(User user, Pageable pageable) {
        QLike like = QLike.like;
        QPost post = QPost.post;

        // 좋아요한 게시물 찾기
        List<Post> posts = queryFactory.select(post)
                .from(like)
                .join(like.post, post)
                .where(like.user.eq(user).and(like.post.isNotNull()))

                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.selectFrom(like)
                .where(like.user.eq(user).and(like.post.isNotNull()))
                .fetchCount();

        return new PageImpl<>(posts, pageable, total);
    }

    // 좋아요한 댓글
    @Override
    public Page<Comment> findLikedComments(User user, Pageable pageable) {
        QLike like = QLike.like;
        QComment comment = QComment.comment;

        List<Comment> comments = queryFactory.select(comment)
                .from(like)
                .join(like.comment, comment)
                .where(like.user.eq(user).and(like.comment.isNotNull()))

                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.selectFrom(like)
                .where(like.user.eq(user).and(like.comment.isNotNull()))
                .fetchCount();

        return new PageImpl<>(comments, pageable, total);
    }
}
