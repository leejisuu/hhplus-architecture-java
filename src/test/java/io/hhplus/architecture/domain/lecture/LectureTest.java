package io.hhplus.architecture.domain.lecture;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class LectureTest {

    @Test
    void 신청_기능한_잔여_좌석수를_1_감소한다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2024, 12, 28, 10, 00, 00); // 2024년 12월 28일 10시 0분 0초

        Lecture lecture = Lecture.builder()
                .title("클린 레이어드 아키텍처 특강")
                .startDateTime(startDateTime)
                .description("클린 레이어드 아키텍처 특강입니다")
                .remainingCapacity(30)
                .build();

        // when
        lecture.deductRemainingCapacity();

        // then
        assertThat(lecture.getRemainingCapacity()).isEqualTo(29);
    }
}