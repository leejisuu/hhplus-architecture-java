package io.hhplus.architecture.interfaces.api.lecture.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
public class LectureEnrollmentRequest {

    @NotEmpty(message = "사용자 id는 필수입니다.")
    private Long userId;

    @NotEmpty(message = "강의 번호는 필수입니다.")
    private Long lectureId;

    @Builder
    private LectureEnrollmentRequest(Long userId, Long lectureId) {
        this.userId = userId;
        this.lectureId = lectureId;
    }

}
