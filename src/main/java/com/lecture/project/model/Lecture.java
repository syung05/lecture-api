package com.lecture.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Lecture extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String place;

    private String lecturer;

    @ColumnDefault("0")
    private int limitNumber;


    @ColumnDefault("0")
    private int participantNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lectureDateTime;

    private String lectureDetail;

    @Builder
    public Lecture(String place, String lecturer, int limitNumber, LocalDateTime lectureDateTime, String lectureDetail) {
        this.place = place;
        this.lecturer = lecturer;
        this.limitNumber = limitNumber;
        this.lectureDateTime = lectureDateTime;
        this.lectureDetail = lectureDetail;
    }
}
