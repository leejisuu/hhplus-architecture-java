package io.hhplus.architecture.domain.lecture;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

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
