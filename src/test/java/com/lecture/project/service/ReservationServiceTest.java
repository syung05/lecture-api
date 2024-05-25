package com.lecture.project.service;

import com.lecture.project.common.exception.BusinessException;
import com.lecture.project.common.exception.CommonException;
import com.lecture.project.dto.ReservationRequest;
import com.lecture.project.dto.ReservationResponse;
import com.lecture.project.dto.ReservationSearch;
import com.lecture.project.model.Lecture;
import com.lecture.project.model.Reservation;
import com.lecture.project.respository.LectureRepository;
import com.lecture.project.respository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    private ReservationService service;
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private LectureRepository lectureRepository;


    @Test
    void getReservation_success() {
        List<ReservationResponse> response = List.of(getResponse(1L), getResponse(2L));
        ReservationSearch search = new ReservationSearch();
        given(reservationRepository.findReservations(search)).willReturn(response);

        List<ReservationResponse> reservations = service.getReservations(search);

        assertThat(reservations.size()).isEqualTo(2);
    }

    @Test
    void createReservation_invalid_participant_fail() {
        ReservationRequest request = new ReservationRequest();
        request.setParticipant("123ab");
        request.setLectureId(1L);

        assertThrows(BusinessException.class, () -> service.createReservation(request));
    }

    @Test
    void createReservation_existReservation_fail() {
        ReservationRequest request = new ReservationRequest();
        request.setParticipant("12345");
        request.setLectureId(1L);


        given(lectureRepository.findByIdLock(request.getLectureId())).willReturn(Optional.of(getLecture(1L)));
        given(reservationRepository.findByLectureIdAndParticipant(request.getLectureId(), request.getParticipant())).willReturn(Optional.of(new Reservation()));

        assertThrows(BusinessException.class, () -> service.createReservation(request));
    }

    @Test
    void createReservation_success() {
        ReservationRequest request = new ReservationRequest();
        request.setParticipant("12345");
        request.setLectureId(1L);

        given(lectureRepository.findByIdLock(request.getLectureId())).willReturn(Optional.of(getLecture(1L)));
        given(reservationRepository.findByLectureIdAndParticipant(request.getLectureId(), request.getParticipant())).willReturn(Optional.empty());

        service.createReservation(request);

        verify(reservationRepository, times(1)).save(any());
    }

    @Test
    void removeReservation_fail() {
        long id = 1L;
        given(reservationRepository.findById(id)).willReturn(Optional.empty());

        assertThrows(CommonException.class, () -> service.removeReservation(id));
    }

    @Test
    void removeReservation_success() {
        long id = 1L;
        Reservation reservation = Reservation.builder()
                .lecture(getLecture(id))
                .build();

        given(reservationRepository.findById(id)).willReturn(Optional.of(reservation));

        service.removeReservation(id);

        verify(reservationRepository, times(1)).delete(any());
    }

    private ReservationResponse getResponse(long id) {
        ReservationResponse response = new ReservationResponse();
        response.setId(id);
        return response;
    }

    private Lecture getLecture(long id) {
        Lecture lecture = new Lecture();
        lecture.setId(id);
        lecture.setLimitNumber(5);
        return lecture;
    }
}