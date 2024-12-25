package io.hhplus.architecture.interfaces.api.lecture.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class LectureEnrollmentRequest {

    private Long userId;
    private Long lectureId;

    @Builder
    private LectureEnrollmentRequest(Long userId, Long lectureId) {
        this.userId = userId;
        this.lectureId = lectureId;
    }

}
