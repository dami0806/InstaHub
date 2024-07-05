package com.sparta.instahub.domain.comment.service;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.auth.entity.UserRole;
import com.sparta.instahub.domain.auth.exception.UnauthorizedException;
import com.sparta.instahub.domain.auth.service.UserService;
import com.sparta.instahub.domain.comment.dto.CommentRequestDto;
import com.sparta.instahub.domain.comment.dto.CommentResponseDto;
import com.sparta.instahub.domain.comment.entity.Comment;
import com.sparta.instahub.domain.comment.exception.InaccessibleCommentException;
import com.sparta.instahub.domain.comment.repository.CommentRepository;
import com.sparta.instahub.domain.post.entity.Post;
import com.sparta.instahub.domain.post.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    @Override
    @Transactional
    public CommentResponseDto createComment(UUID postId, String username, CommentRequestDto requestDto) {
        Post post = postService.getPost(postId);
        User user = userService.getUserByName(username);
        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .contents(requestDto.getContents())
                .build();
        commentRepository.save(comment);

        return CommentResponseDto.commentResponseDto(comment);
    }

    // 댓글 수정
    @Override
    public CommentResponseDto updateComment(UUID commentId, String username, CommentRequestDto requestDto) {
        User user = getCurrentUser(username);

        Comment comment = getComment(commentId);

        validateUserPermission(comment, user);

        comment.update(requestDto.getContents());
        commentRepository.save(comment);

        return CommentResponseDto.commentResponseDto(comment);
    }

    // 댓글 삭제
    @Override
    @Transactional
    public void deleteComment(UUID commentId, String username) {
        User user = getCurrentUser(username);
        Comment comment = getComment(commentId);

        validateUserPermission(comment, user);
        commentRepository.deleteById(commentId);
    }

    // 게시물에 대한 댓글찾기
    @Override
    public Page<CommentResponseDto> getCommentsByPostId(UUID post, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        Page<Comment> commentPage = commentRepository.findByPostId(post, pageable);
        return commentPage.map(CommentResponseDto::commentResponseDto);
    }

    // id로 댓글 불러오기
    @Override
    public Comment getComment(UUID commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new InaccessibleCommentException("comment ID가 잘못된 ID입니다."));

    }

    // 권한이 있는지 확인
    private void validateUserPermission(Comment comment, User currentUser) {
        if (!comment.getUser().equals(currentUser) && currentUser.getUserRole() != UserRole.ADMIN) {
            throw new UnauthorizedException("해당 권한이 없는 사용자 입니다");
        }
    }

    private User getCurrentUser(String username) {
        return userService.getUserByName(username);
    }
}