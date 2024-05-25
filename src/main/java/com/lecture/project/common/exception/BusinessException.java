package com.lecture.project.common.exception;

public class BusinessException extends RuntimeException {
    private Err err;

    public BusinessException(Err err) {
        super(err.message());
    }
}