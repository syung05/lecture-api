package com.lecture.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lecture.project.dto.ReservationRequest;
import com.lecture.project.dto.ReservationResponse;
import com.lecture.project.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @InjectMocks
    private ReservationController controller;

    @Mock
    private ReservationService service;

    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getReservation_success() throws Exception {
        ReservationResponse response = new ReservationResponse();
        response.setParticipant("12345");


        given(service.getReservations(any())).willReturn(Arrays.asList(response));

        mockMvc.perform(get("/reservation")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data.[0].participant", is(response.getParticipant())));
    }

    @Test
    public void createReservation_withOut_value_fail() throws Exception {
        ReservationRequest request = new ReservationRequest();

        mockMvc.perform(post("/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void createReservation_invalid_participant_fail2() throws Exception {
        ReservationRequest request = new ReservationRequest();
        request.setLectureId(1L);
        request.setParticipant("123");

        mockMvc.perform(post("/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void createLecture_success() throws Exception {
        ReservationRequest request = new ReservationRequest();
        request.setLectureId(1L);
        request.setParticipant("12345");

        mockMvc.perform(post("/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(service).createReservation(any());
    }

    @Test
    public void deleteReservation_success() throws Exception {
        long reservationId = 1;

        mockMvc.perform(delete("/reservation/{reservationId}", reservationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).removeReservation(1);
    }
}