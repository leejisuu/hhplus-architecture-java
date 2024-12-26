package io.hhplus.architecture.domain.lecture;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    private LectureEnrollment(Long userId, Long lectureId, String title, LocalDateTime startDateTime, String description, String lecturerName) {
        this.userId = userId;
        this.lectureId = lectureId;
        this.title = title;
        this.startDateTime = startDateTime;
        this.description = description;
        this.lecturerName = lecturerName;
    }

    public static LectureEnrollment create(Lecture lecture, long userId) {
        return LectureEnrollment.builder()
                .userId(userId)
                .lectureId(lecture.getId())
                .title(lecture.getTitle())
                .startDateTime(lecture.getStartDateTime())
                .description(lecture.getDescription())
                .lecturerName(lecture.getLecturerName())
                .build();
    }
}
