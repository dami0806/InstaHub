package com.sparta.instahub.domain.like.service;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.auth.service.UserService;
import com.sparta.instahub.domain.comment.entity.Comment;
import com.sparta.instahub.domain.comment.service.CommentService;
import com.sparta.instahub.domain.like.entity.Like;
import com.sparta.instahub.domain.like.entity.LikeType;
import com.sparta.instahub.domain.like.repository.LikeRepository;
import com.sparta.instahub.domain.post.entity.Post;
import com.sparta.instahub.domain.post.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    @Transactional
    @Override
    public void likePost(UUID userId, UUID postId) {
        User user = userService.getUserById(userId);
        Post post = postService.getPost(postId);
        validateLike(user, post, null);

        Like like = Like.builder()
                .user(user)
                .post(post)
                .type(LikeType.POST)
                .build();
        likeRepository.save(like);
    }

    @Transactional
    @Override
    public void unlikePost(UUID userId, UUID postId) {
        User user = userService.getUserById(userId);
        Post post = postService.getPost(postId);
        Like like = findLike(user, post, null);

        likeRepository.delete(like);
    }

    @Transactional
    @Override
    public void likeComment(UUID userId, UUID commentId) {
        User user = userService.getUserById(userId);
        Comment comment = commentService.getComment(commentId);
        validateLike(user, null, comment);

        Like like = Like.builder()
                .user(user)
                .comment(comment)
                .type(LikeType.COMMENT)
                .build();
        likeRepository.save(like);
    }

    @Transactional
    @Override
    public void unlikeComment(UUID userId, UUID commentId) {
        User user = userService.getUserById(userId);
        Comment comment = commentService.getComment(commentId);
        Like like = findLike(user, null, comment);

        likeRepository.delete(like);
    }

    private void validateLike(User user, Post post, Comment comment) {
        if (post != null && likeRepository.existsByUserAndPost(user, post)) {
            throw new IllegalArgumentException("이미 이 포스트에 좋아요를 눌렀습니다.");
        }
        if (comment != null && likeRepository.existsByUserAndComment(user, comment)) {
            throw new IllegalArgumentException("이미 이 댓글에 좋아요를 눌렀습니다.");
        }
    }

    private Like findLike(User user, Post post, Comment comment) {
        Optional<Like> likeOpt = post != null
                ? likeRepository.findByUserAndPost(user, post)
                : likeRepository.findByUserAndComment(user, comment);
        return likeOpt.orElseThrow(() -> new IllegalArgumentException("좋아요한 게시물 또는 댓글이 없습니다."));
    }
}