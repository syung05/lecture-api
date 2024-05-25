package com.lecture.project.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    private String participant;


    @Builder
    public Reservation(Lecture lecture, String participant) {
        this.lecture = lecture;
        this.participant = participant;
    }
}
