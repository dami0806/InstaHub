package com.sparta.instahub.s3.exception;

public class InvalidImageException extends RuntimeException{

    public InvalidImageException(String message) {
        super(message);
    }
}
