package com.sparta.instahub.domain.post.exception;

public class InaccessiblePostException extends RuntimeException {
    public InaccessiblePostException(String message) {
        super(message);
    }
}
