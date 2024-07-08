package com.sparta.instahub.domain.like.service;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.auth.service.UserService;
import com.sparta.instahub.domain.comment.dto.CommentResponseDto;
import com.sparta.instahub.domain.comment.entity.Comment;
import com.sparta.instahub.domain.comment.service.CommentService;
import com.sparta.instahub.domain.like.entity.Like;
import com.sparta.instahub.domain.like.entity.LikeType;
import com.sparta.instahub.domain.like.repository.LikeRepository;
import com.sparta.instahub.domain.post.dto.PostResponseDto;
import com.sparta.instahub.domain.post.entity.Post;
import com.sparta.instahub.domain.post.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;

    private final UserService userService;

    @Transactional
    @Override
    public void likePost(UUID userId, UUID postId) {
        User user = getCurrentUser(userId);
        validateLike(user, postId, null);

        Like like = Like.builder()
                .user(user)
                .post(postId)
                .type(LikeType.POST)
                .build();
        likeRepository.save(like);
    }

    @Transactional
    @Override
    public void unlikePost(UUID userId, UUID postId) {
        User user = getCurrentUser(userId);

        Like like = findLike(user, postId, null);

        likeRepository.delete(like);
    }

    @Transactional
    @Override
    public void likeComment(UUID userId, UUID commentId) {
        User user = getCurrentUser(userId);
        validateLike(user, null, commentId);

        Like like = Like.builder()
                .user(user)
                .comment(commentId)
                .type(LikeType.COMMENT)
                .build();
        likeRepository.save(like);
    }

    @Transactional
    @Override
    public void unlikeComment(UUID userId, UUID commentId) {

        User user = getCurrentUser(userId);
        Like like = findLike(user, null, commentId);

        likeRepository.delete(like);
    }

    @Override
    public long countLikesByPostId(UUID postId) {
        return likeRepository.countByPostId(postId);
    }

    private void validateLike(User user, UUID post, UUID comment) {
        if (post != null && likeRepository.existsByUserAndPostId(user, post)) {
            throw new IllegalArgumentException("이미 이 포스트에 좋아요를 눌렀습니다.");
        }
        if (comment != null && likeRepository.existsByUserAndCommentId(user, comment)) {
            throw new IllegalArgumentException("이미 이 댓글에 좋아요를 눌렀습니다.");
        }
    }

    private Like findLike(User user, UUID post, UUID comment) {
        Optional<Like> likeOpt = post != null
                ? likeRepository.findByUserAndPostId(user, post)
                : likeRepository.findByUserAndCommentId(user, comment);
        return likeOpt.orElseThrow(() -> new IllegalArgumentException("좋아요한 게시물 또는 댓글이 없습니다."));
    }

    private User getCurrentUser(UUID userId) {
        return userService.getUserById(userId);
    }


}
