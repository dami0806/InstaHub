package com.sparta.instahub.exception;

import java.io.IOException;

public class InaccessibleImageException extends RuntimeException {
    public InaccessibleImageException(String message) {
        super(message);
    }
}
