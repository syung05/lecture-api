package com.lecture.project.respository;

import com.lecture.project.dto.ReservationResponse;
import com.lecture.project.dto.ReservationSearch;

import java.util.List;

public interface ReservationCustomRepository {

    List<ReservationResponse> findReservations(ReservationSearch search);

    List<ReservationResponse> findPopularReservations();
}
