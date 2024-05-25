package com.lecture.project.respository;

import com.lecture.project.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationCustomRepository {
    Optional<Reservation> findByLectureIdAndParticipant(long id, String participant);

    List<Reservation> findByLectureId(long id);

}
