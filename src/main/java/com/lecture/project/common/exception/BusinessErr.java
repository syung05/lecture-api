package com.lecture.project.common.exception;

public enum BusinessErr implements Err {
    EXIST_RESERVATION("400", "이미 신청한 강의입니다."),
    EXCEED_PARTICIPANT("401", "신청 가능 인원 수를 초과하였습니다."),

    INVALID_PARTICIPANT("402", "유효하지 않은 사번입니다.");

    private final String code;
    private final String message;

    BusinessErr(String code, String message) {
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