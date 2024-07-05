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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    private final PostService postService;
    private final CommentService commentService;

    @Transactional
    @Override
    public void likePost(UUID userId, UUID postId) {
        User user = getCurrentUser(userId);
        Post post = getPost(postId);
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
        User user = getCurrentUser(userId);
        Post post = getPost(postId);

        Like like = findLike(user, post, null);

        likeRepository.delete(like);
    }

    @Transactional
    @Override
    public void likeComment(UUID userId, UUID commentId) {
        User user = getCurrentUser(userId);
        Comment comment = getComment(commentId);
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

        User user = getCurrentUser(userId);
        Comment comment = getComment(commentId);
        Like like = findLike(user, null, comment);

        likeRepository.delete(like);
    }

    @Override
    public Page<PostResponseDto> getLikedPosts(UUID userId, Pageable pageable) {
        User user = getCurrentUser(userId);
        Page<Post> likedPosts = likeRepository.findLikedPosts(user, pageable);
        return likedPosts.map(PostResponseDto::new);
    }

    @Override
    public Page<CommentResponseDto> getLikedComments(UUID userId, Pageable pageable) {
        User user = getCurrentUser(userId);
        Page<Comment> likedComments = likeRepository.findLikedComments(user, pageable);
        return likedComments.map(CommentResponseDto::new);
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

    private User getCurrentUser(UUID userId) {
        return userService.getUserById(userId);
    }

    private Comment getComment(UUID commentId) {
        return commentService.getComment(commentId);
    }

    private Post getPost(UUID postId) {
        return postService.getPost(postId);
    }
}
