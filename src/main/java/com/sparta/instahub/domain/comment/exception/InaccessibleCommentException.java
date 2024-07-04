package com.sparta.instahub.domain.comment.exception;

public class InaccessibleCommentException extends RuntimeException {
    public InaccessibleCommentException(String message) {
        super(message);
    }
}
