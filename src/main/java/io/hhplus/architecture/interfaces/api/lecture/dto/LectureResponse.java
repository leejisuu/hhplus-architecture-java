package io.hhplus.architecture.interfaces.api.lecture.dto;

import io.hhplus.architecture.domain.lecture.Lecture;
import lombok.Builder;

import java.time.LocalDateTime;

public class LectureResponse {

    private Long id;
    private String title;
    private LocalDateTime startDateTime;
    private String description;
    private String lecturerName;

    @Builder
    private LectureResponse(Long id, String title, LocalDateTime startDateTime, String description, String lecturerName) {
        this.id = id;
        this.title = title;
        this.startDateTime = startDateTime;
        this.description = description;
        this.lecturerName = lecturerName;
    }

    public static LectureResponse of(Lecture lecture) {
        return LectureResponse.builder()
                .id(lecture.getId())
                .title(lecture.getTitle())
                .startDateTime(lecture.getStartDateTime())
                .description(lecture.getDescription())
                .lecturerName(lecture.getLecturerName())
                .build();
    }
}
