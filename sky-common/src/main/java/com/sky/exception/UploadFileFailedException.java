package com.sky.exception;

public class UploadFileFailedException extends RuntimeException {
    public UploadFileFailedException() {
    }
    public UploadFileFailedException(String message) {
        super(message);
    }
}
