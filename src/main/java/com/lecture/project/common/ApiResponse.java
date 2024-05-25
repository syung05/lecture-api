package com.lecture.project.common;

import com.lecture.project.common.exception.Err;

public class ApiResponse<T> {
    private final String code;
    private final String message;
    private final T data;

    public static final String SUCCESS_CODE = "200";
    public static final String SUCCESS_MESSAGE = "SUCCESS";

    public ApiResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public ApiResponse(T data) {
        this.code = SUCCESS_CODE;
        this.message = SUCCESS_MESSAGE;
        this.data = data;
    }

    public ApiResponse(Err err) {
        this.code = err.code();
        this.message = err.message();
        this.data = null;
    }

    public static ApiResponse success() {
        return new ApiResponse(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public static ApiResponse fail(Err err) {
        return new ApiResponse(err);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}