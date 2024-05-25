package com.lecture.project.service;

import com.lecture.project.dto.LectureRequest;
import com.lecture.project.dto.LectureResponse;
import com.lecture.project.model.Lecture;
import com.lecture.project.respository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository repository;

    public List<LectureResponse> getLecture(boolean showAll) {
        LocalDateTime now = LocalDateTime.now();
        List<Lecture> lectures = showAll ? repository.findAll() :
                repository.findAllByLectureDateTimeBetween(now.minusDays(1), now.plusDays(7));
        return lectures.stream().map(LectureResponse::of).collect(Collectors.toList());
    }


    public void createLecture(LectureRequest request) {
        repository.save(mapToLecture(request));
    }

    private Lecture mapToLecture(LectureRequest request) {
        return Lecture.builder()
                .place(request.getPlace())
                .lecturer(request.getLecturer())
                .limitNumber(request.getLimitNumber())
                .lectureDateTime(request.getLectureDateTime())
                .lectureDetail(request.getLectureDetail())
                .build();
    }
}
