package com.lecture.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lecture.project.model.Lecture;
import com.lecture.project.model.Reservation;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationResponse {
    private Long id;

    private Lecture lecture;

    private String participant;

    private LocalDateTime dateTime;

    @QueryProjection
    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.lecture = reservation.getLecture();
        this.participant = reservation.getParticipant();
        this.dateTime = reservation.getCreatedDate();
    }

    @QueryProjection
    public ReservationResponse(Lecture lecture, Long count) {
        this.lecture = lecture;
    }
}
