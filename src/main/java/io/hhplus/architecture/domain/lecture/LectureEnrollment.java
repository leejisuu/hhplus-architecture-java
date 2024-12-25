package io.hhplus.architecture.domain.lecture;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class LectureEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long lectureId;

    private String title;

    private LocalDateTime startDateTime;

    private String description;

    private String lecturerName;

    private LocalDateTime createdAt;

    public LectureEnrollment() {}

    @Builder
    private LectureEnrollment(Long userId, Long lectureId, String title, LocalDateTime startDateTime, String description, String lecturerName, LocalDateTime createdAt) {
        this.userId = userId;
        this.lectureId = lectureId;
        this.title = title;
        this.startDateTime = startDateTime;
        this.description = description;
        this.lecturerName = lecturerName;
        this.createdAt = createdAt;
    }

    public static LectureEnrollment create(Lecture lecture, long userId, LocalDateTime createdAt) {
        return LectureEnrollment.builder()
                .userId(userId)
                .lectureId(lecture.getId())
                .title(lecture.getTitle())
                .startDateTime(lecture.getStartDateTime())
                .description(lecture.getDescription())
                .lecturerName(lecture.getLecturerName())
                .createdAt(createdAt)
                .build();
    }
}
