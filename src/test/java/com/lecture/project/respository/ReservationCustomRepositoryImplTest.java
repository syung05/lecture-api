package com.lecture.project.respository;

import com.lecture.project.dto.ReservationResponse;
import com.lecture.project.dto.ReservationSearch;
import com.lecture.project.model.Lecture;
import com.lecture.project.model.Reservation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationCustomRepositoryImplTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private LectureRepository lectureRepository;

    private Lecture lecture;


    @BeforeAll
    void init() {
        reservationRepository.deleteAll();
        lectureRepository.deleteAll();

        lectureRepository.saveAll(List.of(getLecture(), getLecture(), getLecture()));
        List<Lecture> lectures = lectureRepository.findAll();
        lecture = lectures.get(2);
        List<Reservation> reservations = List.of(getReservation(lectures.get(0), "12345"),
                getReservation(lectures.get(0), "12346"),
                getReservation(lectures.get(0), "12347"),
                getReservation(lectures.get(1), "12345"),
                getReservation(lectures.get(2), "12347"),
                getReservation(lectures.get(2), "12348"),
                getReservation(lectures.get(2), "12349"));
        reservationRepository.saveAll(reservations);
    }


    @Test
    void findReservations_byParticipant_success() {
        ReservationSearch search = new ReservationSearch();
        search.setParticipant("12345");

        List<ReservationResponse> result = reservationRepository.findReservations(search);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getParticipant()).isEqualTo("12345");
    }

    @Test
    void findReservations_byLectureId_success() {
        ReservationSearch search = new ReservationSearch();
        search.setLectureId(lecture.getId());

        List<ReservationResponse> result = reservationRepository.findReservations(search);
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getLecture().getId()).isEqualTo(lecture.getId());
    }

    @Test
    void findPopularLecture_success() {
        List<ReservationResponse> result = reservationRepository.findPopularReservations();
        assertThat(result.size()).isEqualTo(3);

        List<Reservation> all = reservationRepository.findAll()
                .stream()
                .filter(reservation -> reservation.getLecture().getId().equals(lecture.getId()))
                .collect(Collectors.toList());

        all.get(0).setCreatedDate(LocalDateTime.now().minusDays(3));
        all.get(1).setCreatedDate(LocalDateTime.now().minusDays(4));
        all.get(2).setCreatedDate(LocalDateTime.now().minusDays(5));

        reservationRepository.saveAll(all);

        result = reservationRepository.findPopularReservations();
        assertThat(result.size()).isEqualTo(2);
    }

    private Lecture getLecture() {
        return Lecture.builder()
                .place("place")
                .lecturer("lecturer")
                .limitNumber(5)
                .lectureDateTime(LocalDateTime.now().plusDays(2))
                .build();
    }

    private Reservation getReservation(Lecture lecture, String participant) {
        return Reservation.builder()
                .lecture(lecture)
                .participant(participant)
                .build();
    }
}