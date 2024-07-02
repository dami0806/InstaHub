package com.sparta.instahub.domain.comment.service;

import com.sparta.instahub.domain.auth.entity.User;
import com.sparta.instahub.domain.auth.service.UserService;
import com.sparta.instahub.domain.comment.Repository.CommentRepository;
import com.sparta.instahub.domain.comment.dto.CommentRequestDto;
import com.sparta.instahub.domain.comment.dto.CommentResponseDto;
import com.sparta.instahub.domain.comment.entity.Comment;
import com.sparta.instahub.domain.post.entity.Post;
import com.sparta.instahub.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserService userService;

//    //댓글 생성
//    public CommentResponseDto createComment (Long postId, CommentRequestDto requestDto, String username){
//        Post post=findPostById(postId);
//        User user = userService.getUserByName(username);
//        Comment comment=new Comment(requestDto, post, user);
//        Comment savedComment=commentRepository.save(comment);
//        return new CommentResponseDto(comment);
//    }

//    //댓글 조회
//    public List<CommentResponseDto> getAllComment(Long postId) {
//        Post post=findPostById(postId);
//        List<Comment> commentList=post.getComments;
//        List<CommentResponseDto> responseDtoList=new ArrayList<>();
//        for(Comment responseDto : commentList){
//            responseDtoList.add(new CommentResponseDto(responseDto));
//        }
//        return responseDtoList;
//    }

//    //댓글 수정
//    @Transactional
//    public CommentResponseDto updateComment(UUID commentId, CommentRequestDto requestDto, String username){
//        Comment comment=findCommentById(commentId);
//        User user=userService.getUserByName(username);
//        if(comment.getUser().getId().equals(user.getId())){
//            comment.update(requestDto);
//            return new CommentResponseDto(comment);
//        }else {
//            throw new IllegalArgumentException("ID가 일치하지 않습니다.");
//        }
//
//
//    }

//    //댓글 삭제
//    public ResponseEntity<String> deleteComment(UUID id, String username){
//        Comment comment=findCommentById(id);
//        User user=userService.getUserByName(username);
//        if(comment.getUser().getId().equals(user.getId())) {
//            commentRepository.delete(comment);
//            return ResponseEntity.ok("댓글이 삭제되었습니다.");
//        }else throw new IllegalArgumentException("ID가 일치하지 않습니다.");
//
//    }

    // id 존재 확인 메서드
//    private Comment findCommentById(UUID id) {
//        return commentRepository.findById(id).orElseThrow(() ->
//                new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));
//    }

    //게시물 id 존재 확인
    private Post findPostById(UUID id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시물을 찾을 수 없습니다."));

    }



}
