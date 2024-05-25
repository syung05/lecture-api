package com.lecture.project.respository;

import com.lecture.project.model.Lecture;
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

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LectureRepositoryTest {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    private LocalDateTime now = LocalDateTime.now();

    @BeforeAll
    void init() {
        reservationRepository.deleteAll();
        lectureRepository.deleteAll();
        List<Lecture> lectures = List.of(getLecture(now),
                getLecture(now.minusDays(1)),
                getLecture(now.minusDays(1).plusSeconds(5)),
                getLecture(now.minusDays(1).minusSeconds(5)),
                getLecture(now.plusDays(5)),
                getLecture(now.plusDays(6)),
                getLecture(now.plusDays(7)),
                getLecture(now.plusDays(7).minusSeconds(5)),
                getLecture(now.plusDays(7).plusSeconds(5)));

        lectureRepository.saveAll(lectures);
    }


    @Test
    void updateLectureParticipant_success() {
        long id = 7;
        lectureRepository.updateLectureParticipant(id, 1);
        Lecture lecture = lectureRepository.findById(id).get();

        assertThat(lecture.getParticipantNumber()).isEqualTo(1);


        lectureRepository.updateLectureParticipant(id, 2);
        lecture = lectureRepository.findById(id).get();
        assertThat(lecture.getParticipantNumber()).isEqualTo(2);
    }

    @Test
    void findAllByLectureDateTimeBetween_success() {
        List<Lecture> result = lectureRepository.findAllByLectureDateTimeBetween(now.minusDays(1), now.plusDays(7));
        assertThat(result.size()).isEqualTo(7);
    }

    private Lecture getLecture(LocalDateTime dateTime) {
        return Lecture.builder()
                .place("place")
                .lecturer("lecturer")
                .limitNumber(5)
                .lectureDateTime(dateTime)
                .build();
    }

}