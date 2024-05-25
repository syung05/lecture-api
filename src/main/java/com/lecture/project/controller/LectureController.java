package com.lecture.project.controller;

import com.lecture.project.common.ApiResponse;
import com.lecture.project.dto.LectureRequest;
import com.lecture.project.service.LectureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lecture")
@RequiredArgsConstructor
public class LectureController {
    private final LectureService service;
    private final CacheManager cacheManager;

    @GetMapping("/cache")
    public Object getCache() {
        Object lecture = cacheManager.getCache("lecture").getNativeCache();
        return lecture;
    }

    @GetMapping
    public ApiResponse getLectures(@RequestParam(required = false) boolean showAll) {
        return new ApiResponse(service.getLecture(showAll));
    }

    @PostMapping
    public ApiResponse createLecture(@Valid @RequestBody LectureRequest request) {
        service.createLecture(request);
        return ApiResponse.success();
    }
}
