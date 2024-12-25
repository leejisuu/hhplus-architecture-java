package io.hhplus.architecture.domain.lecture;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime startDateTime;

    private String description;

    private int remainingCapacity;

    private String lecturerName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Lecture() {}

    @Builder
    private Lecture(String title, LocalDateTime startDateTime, String description, int remainingCapacity, String lecturerName) {
        this.title = title;
        this.startDateTime = startDateTime;
        this.description = description;
        this.remainingCapacity = remainingCapacity;
        this.lecturerName = lecturerName;
    }

    public void deductRemainingCapacity() {
        this.remainingCapacity--;
    }
}
