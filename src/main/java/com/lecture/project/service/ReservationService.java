package com.lecture.project.service;

import com.lecture.project.common.exception.BusinessErr;
import com.lecture.project.common.exception.BusinessException;
import com.lecture.project.common.exception.CommonErr;
import com.lecture.project.common.exception.CommonException;
import com.lecture.project.dto.ReservationRequest;
import com.lecture.project.dto.ReservationResponse;
import com.lecture.project.dto.ReservationSearch;
import com.lecture.project.model.Lecture;
import com.lecture.project.model.Reservation;
import com.lecture.project.respository.LectureRepository;
import com.lecture.project.respository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final LectureRepository lectureRepository;

    public List<ReservationResponse> getReservations(ReservationSearch search) {
        return reservationRepository.findReservations(search);
    }

    @Transactional
    public void createReservation(ReservationRequest request) {
        if (isInvalidParticipantNumber(request.getParticipant())) {
            throw new BusinessException(BusinessErr.INVALID_PARTICIPANT);
        }

        Lecture lecture = lectureRepository.findByIdLock(request.getLectureId()).orElseThrow(() -> new CommonException(CommonErr.ENTITY_NOT_EXIST));
        checkExistingReservation(request);
        int participantNumber = lecture.getParticipantNumber();

        if (lecture.getParticipantNumber() >= lecture.getLimitNumber()) {
            throw new BusinessException(BusinessErr.EXCEED_PARTICIPANT);
        }

        lectureRepository.updateLectureParticipant(lecture.getId(), participantNumber + 1);
        reservationRepository.save(mapToReservation(request, lecture));
    }

    public void removeReservation(long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new CommonException(CommonErr.ENTITY_NOT_EXIST));
        Lecture lecture = reservation.getLecture();

        reservationRepository.delete(reservation);
        lectureRepository.updateLectureParticipant(lecture.getId(), lecture.getParticipantNumber() + 1);
    }

    public List<ReservationResponse> getPopularReservations() {
        return reservationRepository.findPopularReservations();
    }

    private void checkExistingReservation(ReservationRequest request) {
        Optional<Reservation> result = reservationRepository.findByLectureIdAndParticipant(request.getLectureId(), request.getParticipant());

        if (result.isPresent()) {
            throw new BusinessException(BusinessErr.EXIST_RESERVATION);
        }
    }

    private static Reservation mapToReservation(ReservationRequest request, Lecture lecture) {
        return Reservation.builder()
                .lecture(lecture)
                .participant(request.getParticipant())
                .build();
    }

    private boolean isInvalidParticipantNumber(String participant) {
        return !participant.chars().allMatch(Character::isDigit);
    }
}
