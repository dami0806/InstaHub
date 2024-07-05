package com.sparta.instahub.domain.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.instahub.domain.comment.entity.Comment;
import com.sparta.instahub.domain.comment.entity.QComment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Comment> findAllByPostId(UUID postId, Pageable pageable) {
        QComment comment = QComment.comment;
        List<Comment> comments = queryFactory.selectFrom(comment)
                .where(comment.post.id.eq(postId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(comment.createdAt.desc())
                .fetch();
        long total = queryFactory.selectFrom(comment)
                .where(comment.post.id.eq(postId))
                .fetchCount();
        return new PageImpl<>(comments, pageable, total);
    }
}
