package com.lecture.project.common.exception;

public class CommonException extends RuntimeException {
    private Err err;

    public CommonException(Err err) {
        super(err.message());
    }
}
