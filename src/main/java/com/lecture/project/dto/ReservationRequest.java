package com.lecture.project.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReservationRequest {
    @NotNull
    private Long lectureId;

    @NotNull
    @Size(min = 5, max = 5)
    private String participant;
}
