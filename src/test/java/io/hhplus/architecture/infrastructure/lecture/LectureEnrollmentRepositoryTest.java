package io.hhplus.architecture.infrastructure.lecture;

import io.hhplus.architecture.domain.lecture.LectureEnrollment;
import io.hhplus.architecture.domain.lecture.LectureEnrollmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@ActiveProfiles("test")
public class LectureEnrollmentRepositoryTest {

    @Autowired
    private LectureEnrollmentRepository lectureEnrollmentRepository;

    @AfterEach
    void tearDown() {
        lectureEnrollmentRepository.deleteAllInBatch();
    }

    @Test
    void 유저가_특강을_신청한다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2024, 12, 28, 10, 0, 0);

        LectureEnrollment lectureEnrollment = createLectureEnrollment(1L, 1L, "아키텍처 특강", startDateTime, "김항해 강사님의 아키텍처 특강입니다.", "김항해");

        // when
        LectureEnrollment result = lectureEnrollmentRepository.save(lectureEnrollment);

        // then
        assertThat(result)
                .extracting("userId", "lectureId", "title", "startDateTime", "description", "lecturerName")
                .containsExactly(lectureEnrollment.getUserId(), lectureEnrollment.getLectureId(), lectureEnrollment.getTitle(),
                        lectureEnrollment.getStartDateTime(), lectureEnrollment.getDescription(), lectureEnrollment.getLecturerName()
                );
    }

    @Test
    void 유저가_신청한_모든_특강_목록을_신청_일자_기준_내림차순으로_정렬하여_조회한다() {
        // given
        Long userId = 1L;

        LocalDateTime startDateTime1 = LocalDateTime.of(2024, 10, 20, 11, 0, 0);
        LocalDateTime startDateTime2 = LocalDateTime.of(2024, 11, 25, 16, 0, 0);
        LocalDateTime startDateTime3 = LocalDateTime.of(2024, 12, 28, 18, 0, 0);

        LectureEnrollment enroll1 = createLectureEnrollment(userId, 1L, "특강1", startDateTime1, "설명1", "강사1");
        LectureEnrollment enroll2 = createLectureEnrollment(userId, 4L, "특강2", startDateTime2, "설명2", "강사2");
        LectureEnrollment enroll3 = createLectureEnrollment(userId, 5L, "특강3", startDateTime3, "설명3", "강사3");

        lectureEnrollmentRepository.saveAll(List.of(enroll1, enroll2, enroll3));

        // when
        List<LectureEnrollment> results = lectureEnrollmentRepository.findAllByUserIdOrderByCreatedAtDesc(userId);

        // then
        assertThat(results).hasSize(3)
                .extracting("userId", "lectureId", "title", "startDateTime", "description", "lecturerName")
                .containsExactly(
                        tuple(enroll3.getUserId(), enroll3.getLectureId(), enroll3.getTitle(), enroll3.getStartDateTime(), enroll3.getDescription(), enroll3.getLecturerName()),
                        tuple(enroll2.getUserId(), enroll2.getLectureId(), enroll2.getTitle(), enroll2.getStartDateTime(), enroll2.getDescription(), enroll2.getLecturerName()),
                        tuple(enroll1.getUserId(), enroll1.getLectureId(), enroll1.getTitle(), enroll1.getStartDateTime(), enroll1.getDescription(), enroll1.getLecturerName())
                );
    }

    private static LectureEnrollment createLectureEnrollment(Long userId, Long lectureId, String title, LocalDateTime startDateTime, String description, String lecturerName) {
        return LectureEnrollment.builder()
                .userId(userId)
                .lectureId(lectureId)
                .title(title)
                .startDateTime(startDateTime)
                .description(description)
                .lecturerName(lecturerName)
                .build();
    }
}
