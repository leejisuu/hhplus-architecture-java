package io.hhplus.architecture.domain.lecture;

import io.hhplus.architecture.domain.common.BaseAuditEntity;
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
            throw new RuntimeException("신청 가능한 최대 정원이 초과되었습니다.");
        }

    }
}
