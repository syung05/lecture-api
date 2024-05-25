package com.lecture.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LectureRequest {

    @NotNull
    private String place;

    @NotNull
    private String lecturer;

    @Min(0)
    private int limitNumber;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lectureDateTime;

    private String lectureDetail;
}
