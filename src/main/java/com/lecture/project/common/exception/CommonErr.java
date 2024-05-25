package com.lecture.project.common.exception;

public enum CommonErr implements Err {
    ENTITY_NOT_EXIST("400", "존재하지 않는 객체입니다.");

    private final String code;
    private final String message;

    CommonErr(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
