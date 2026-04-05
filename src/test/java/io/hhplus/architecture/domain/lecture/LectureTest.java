package io.hhplus.architecture.domain.lecture;

import io.hhplus.architecture.support.exception.CustomException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LectureTest {

    @Test
    void 신청_기능한_잔여_좌석수가_1보다_크면_1을_감소한다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2024, 12, 28, 10, 00, 00); // 2024년 12월 28일 10시 0분 0초

        Lecture lecture = Lecture.builder()
                .title("클린 레이어드 아키텍처 특강")
                .startDateTime(startDateTime)
                .description("클린 레이어드 아키텍처 특강입니다")
                .remainingCapacity(1)
                .build();

        // when
        lecture.deductRemainingCapacity();

        // then
        assertThat(lecture.getRemainingCapacity()).isEqualTo(0);
    }

    @Test
    void 신청_기능한_잔여_좌석수가_1보다_작으면_예외를_발생한다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2024, 12, 28, 10, 00, 00); // 2024년 12월 28일 10시 0분 0초

        Lecture lecture = Lecture.builder()
                .title("클린 레이어드 아키텍처 특강")
                .startDateTime(startDateTime)
                .description("클린 레이어드 아키텍처 특강입니다")
                .remainingCapacity(0)
                .build();

        // when // then
        assertThatThrownBy(() -> lecture.deductRemainingCapacity())
                .isInstanceOf(CustomException.class)
                .hasMessage("신청 가능한 최대 정원이 초과되었습니다.");
    }
}