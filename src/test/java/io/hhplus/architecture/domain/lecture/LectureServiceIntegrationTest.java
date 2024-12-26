package io.hhplus.architecture.domain.lecture;

import io.hhplus.architecture.interfaces.api.lecture.dto.LectureEnrollmentRequest;
import io.hhplus.architecture.interfaces.api.lecture.dto.LectureEnrollmentResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
public class LectureServiceIntegrationTest {

    @Autowired
    private LectureService lectureService;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private LectureEnrollmentRepository lectureEnrollmentRepository;

    @AfterEach
    void tearDown() {
        lectureEnrollmentRepository.deleteAllInBatch();
        lectureRepository.deleteAllInBatch();
    }

    @Test
    void 신청_기능한_잔여_좌석수가_1_이상이면_신청_성공한다() {
        // given
        Long userId = 1L;
        Long lectureId = 1L;

        LocalDateTime startDateTime = LocalDateTime.of(2024, 12, 28, 10, 0, 0);

        Lecture lecture = lectureRepository.save(createLecture("특강", startDateTime, "설명", 1, "강사"));

        LectureEnrollmentRequest request = LectureEnrollmentRequest.builder()
                .userId(userId)
                .lectureId(lectureId)
                .build();

        // when
        LectureEnrollmentResponse response = lectureService.saveLectureEnrollment(request);

        // then
        assertThat(response).isNotNull()
                .extracting("userId", "lectureId", "title", "startDateTime", "description", "lecturerName")
                .containsExactly(userId, lectureId, lecture.getTitle(), lecture.getStartDateTime(), lecture.getDescription(), lecture.getLecturerName());
    }

    @Test
    void 신청_기능한_잔여_좌석수가_0_이하면_예외를_발생하고_신청_실패한다() {
        // given
        Long userId = 1L;
        Long lectureId = 2L;

        LocalDateTime startDateTime = LocalDateTime.of(2024, 12, 28, 10, 0, 0);

        lectureRepository.save(createLecture("특강", startDateTime, "설명", 0, "강사"));

        LectureEnrollmentRequest request = LectureEnrollmentRequest.builder()
                .userId(userId)
                .lectureId(lectureId)
                .build();

        // when // then
        assertThatThrownBy(() -> lectureService.saveLectureEnrollment(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("신청 가능한 최대 정원이 초과되었습니다.");

    }

    private static Lecture createLecture(String title, LocalDateTime startDateTime, String description, int remainingCapacity, String lecturerName) {
        return Lecture.builder()
                .title(title)
                .startDateTime(startDateTime)
                .description(description)
                .remainingCapacity(remainingCapacity)
                .lecturerName(lecturerName)
                .build();
    }
}
