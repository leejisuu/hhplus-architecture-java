package io.hhplus.architecture.domain.lecture;

import io.hhplus.architecture.domain.common.BaseAuditEntity;
import io.hhplus.architecture.support.exception.CustomException;
import io.hhplus.architecture.support.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Lecture extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime startDateTime;

    private String description;

    private int remainingCapacity;

    private String lecturerName;

    @Builder
    private Lecture(String title, LocalDateTime startDateTime, String description, int remainingCapacity, String lecturerName) {
        this.title = title;
        this.startDateTime = startDateTime;
        this.description = description;
        this.remainingCapacity = remainingCapacity;
        this.lecturerName = lecturerName;
    }

    public void deductRemainingCapacity() {
        if (remainingCapacity >= 1) {
            this.remainingCapacity--;
        } else {
            throw new CustomException(ErrorCode.LECTURE_FULL);
        }

    }
}
