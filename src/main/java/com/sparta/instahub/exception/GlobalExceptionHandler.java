package com.sparta.instahub.exception;

import com.sparta.instahub.domain.auth.exception.UnauthorizedException;
import com.sparta.instahub.domain.comment.exception.InaccessibleCommentException;
import com.sparta.instahub.domain.post.exception.InaccessiblePostException;
import com.sparta.instahub.s3.exception.InvalidImageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    // post게시물 에러
    @ExceptionHandler(InaccessiblePostException.class)
    public ResponseEntity<String> inaccessiblePostException(InaccessiblePostException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // 사용자오류
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> UnauthorizedExceptionHandler(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(InaccessibleImageException.class)
    public ResponseEntity<String> InaccessibleImageExceptionHandler(InaccessibleImageException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidImageException.class)
    public ResponseEntity<String> InaccessibleImageExceptionHandler(InvalidImageException ex) {
        return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(ex.getMessage());
    }

    @ExceptionHandler(InaccessibleCommentException.class)
    public ResponseEntity<String> InaccessibleCommentExceptionHandler(InaccessibleCommentException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
