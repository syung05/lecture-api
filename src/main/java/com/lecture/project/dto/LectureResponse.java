package com.lecture.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lecture.project.model.Lecture;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LectureResponse {

    private Long id;

    private String place;

    private String lecturer;

    private int limitNumber;

    private int participantNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lectureDateTime;

    private String lectureDetail;

    @Builder
    public LectureResponse(Long id, String place, String lecturer, int limitNumber, int participantNumber, LocalDateTime lectureDateTime, String lectureDetail) {
        this.id = id;
        this.place = place;
        this.lecturer = lecturer;
        this.limitNumber = limitNumber;
        this.participantNumber = participantNumber;
        this.lectureDateTime = lectureDateTime;
        this.lectureDetail = lectureDetail;
    }

    @QueryProjection
    public LectureResponse(Lecture lecture) {
        this.id = lecture.getId();
        this.place = lecture.getPlace();
        this.lecturer = lecture.getLecturer();
        this.limitNumber = lecture.getLimitNumber();
        this.participantNumber = lecture.getParticipantNumber();
        this.lectureDateTime = lecture.getLectureDateTime();
        this.lectureDetail = lecture.getLectureDetail();
    }

    @Builder
    public static LectureResponse of(Lecture lecture) {
        return LectureResponse.builder()
                .id(lecture.getId())
                .place(lecture.getPlace())
                .lecturer(lecture.getLecturer())
                .limitNumber(lecture.getLimitNumber())
                .lectureDateTime(lecture.getLectureDateTime())
                .lectureDetail(lecture.getLectureDetail())
                .build();
    }
}
