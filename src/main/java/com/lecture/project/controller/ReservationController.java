package com.lecture.project.controller;

import com.lecture.project.common.ApiResponse;
import com.lecture.project.dto.ReservationRequest;
import com.lecture.project.dto.ReservationSearch;
import com.lecture.project.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService service;

    @GetMapping
    public ApiResponse getReservations(ReservationSearch search) {
        return new ApiResponse(service.getReservations(search));
    }

    @GetMapping("/popular")
    public ApiResponse getPopularReservations() {
        return new ApiResponse(service.getPopularReservations());
    }

    @PostMapping
    public ApiResponse createReservation(@Valid @RequestBody ReservationRequest request) {
        service.createReservation(request);
        return ApiResponse.success();
    }

    @DeleteMapping("/{reservationId}")
    public ApiResponse removeReservation(@PathVariable("reservationId") long id) {
        service.removeReservation(id);
        return ApiResponse.success();
    }
}
