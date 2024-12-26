package io.hhplus.architecture.interfaces.api.lecture.dto;

import io.hhplus.architecture.domain.lecture.Lecture;
import io.hhplus.architecture.domain.lecture.LectureEnrollment;
import io.hhplus.architecture.domain.user.User;
import lombok.Builder;

import java.time.LocalDateTime;

public class LectureEnrollmentResponse {

    private Long id;
    private Long userId;
    private Long lectureId;
    private String title;
    private LocalDateTime startDateTime;
    private String description;
    private String lecturerName;
    private LocalDateTime createdAt;

    @Builder
    private LectureEnrollmentResponse(Long id, Long userId, Long lectureId, String title, LocalDateTime startDateTime, String description, String lecturerName, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.lectureId = lectureId;
        this.title = title;
        this.startDateTime = startDateTime;
        this.description = description;
        this.lecturerName = lecturerName;
        this.createdAt = createdAt;
    }

    public static LectureEnrollmentResponse of(LectureEnrollment lectureEnrollment) {
        return LectureEnrollmentResponse.builder()
                .id(lectureEnrollment.getId())
                .userId(lectureEnrollment.getUserId())
                .lectureId(lectureEnrollment.getLectureId())
                .title(lectureEnrollment.getTitle())
                .startDateTime(lectureEnrollment.getStartDateTime())
                .description(lectureEnrollment.getDescription())
                .lecturerName(lectureEnrollment.getLecturerName())
                .createdAt(lectureEnrollment.getCreatedAt())
                .build();
    }

}
