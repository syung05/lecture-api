package com.lecture.project.service;

import com.lecture.project.dto.LectureRequest;
import com.lecture.project.dto.LectureResponse;
import com.lecture.project.model.Lecture;
import com.lecture.project.respository.LectureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {
    @InjectMocks
    private LectureService service;
    @Mock
    private LectureRepository repository;


    @Test
    void getLecture_showAll_true_success() {
        List<Lecture> response = List.of(getLecture(1L), getLecture(2L));
        given(repository.findAll()).willReturn(response);

        List<LectureResponse> lectures = service.getLecture(true);

        assertThat(lectures.size()).isEqualTo(2);
        verify(repository, times(1)).findAll();
    }

    @Test
    void getLecture_showAll_false_success() {
        List<Lecture> response = List.of(getLecture(1L), getLecture(2L));
        given(repository.findAllByLectureDateTimeBetween(any(), any())).willReturn(response);

        List<LectureResponse> lectures = service.getLecture(false);

        assertThat(lectures.size()).isEqualTo(2);
        verify(repository, times(1)).findAllByLectureDateTimeBetween(any(), any());
    }

    @Test
    void createLecture_success() {
        given(repository.save(any())).willReturn(getLecture(1L));

        service.createLecture(new LectureRequest());

        verify(repository, times(1)).save(any());
    }

    private Lecture getLecture(long id) {
        Lecture lecture = new Lecture();
        lecture.setId(id);
        return lecture;
    }


}