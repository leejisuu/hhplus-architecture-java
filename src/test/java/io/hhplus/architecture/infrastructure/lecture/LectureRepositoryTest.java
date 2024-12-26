package io.hhplus.architecture.infrastructure.lecture;

import io.hhplus.architecture.domain.lecture.Lecture;
import io.hhplus.architecture.domain.lecture.LectureRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles("test")
@SpringBootTest
class LectureRepositoryTest {

    @Autowired
    private  LectureRepository lectureRepository;

    @AfterEach
    void tearDown() {
        lectureRepository.deleteAllInBatch();
    }

    @Test
    void 강의_정보를_저장한다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2024, 12, 28, 10, 0, 0);

        Lecture lecture = createLecture("아키텍처 특강", startDateTime, "김항해 강사님의 아키텍처 특강입니다.", 30, "김항해");

        // when
        Lecture result = lectureRepository.save(lecture);

        // then
        assertThat(result)
                .extracting("title", "startDateTime", "description", "remainingCapacity", "lecturerName")
                .containsExactly(lecture.getTitle(), lecture.getStartDateTime(), lecture.getDescription(),
                        lecture.getRemainingCapacity(), lecture.getLecturerName());
    }

    @Transactional
    @Test
    void 강의_id로_강의_정보를_조회한다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2024, 12, 28, 10, 0, 0);

        Lecture lecture = createLecture("아키텍처 특강", startDateTime, "김항해 강사님의 아키텍처 특강입니다.", 30, "김항해");
        Lecture savedLecture = lectureRepository.save(lecture);

        // when
        Lecture result = lectureRepository.findByIdWithPessimisticLock(savedLecture.getId());

        // then
        assertThat(result)
                .extracting("title", "startDateTime", "description", "remainingCapacity", "lecturerName")
                .containsExactly(savedLecture.getTitle(), savedLecture.getStartDateTime(), savedLecture.getDescription(),
                        savedLecture.getRemainingCapacity(), savedLecture.getLecturerName());

    }

    @Test
    void 기준_일자_시간_이후에_시작하는_신청_가능한_강의만_조회된다() {
        // given
        LocalDateTime standardDateTime = LocalDateTime.of(2024, 12, 28, 10, 59, 0);

        LocalDateTime startDateTime = LocalDateTime.of(2024, 12, 28, 11, 0, 0);

        Lecture lecture = createLecture("아키텍처 특강", startDateTime, "김항해 강사님의 아키텍처 특강입니다.", 30, "김항해");
        Lecture savedLecture = lectureRepository.save(lecture);

        // when
        List<Lecture> results = lectureRepository.findByStartDateTimeAfterOrderByStartDateTime(standardDateTime);

        // then
        assertThat(results).hasSize(1)
                .extracting("title", "startDateTime", "description", "remainingCapacity", "lecturerName")
                .containsExactly(
                        tuple(savedLecture.getTitle(), savedLecture.getStartDateTime(), savedLecture.getDescription(),
                                savedLecture.getRemainingCapacity(), savedLecture.getLecturerName())
                );
    }

    @Test
    void 기준_일자_시간_이하인_시간에_시작하는_신청_불가능한_강의는_조회되지_않는다() {
        // given
        LocalDateTime standardDateTime = LocalDateTime.of(2024, 12, 28, 10, 0, 0);

        LocalDateTime startDateTime = LocalDateTime.of(2024, 12, 28, 10, 0, 0);

        Lecture lecture = createLecture("아키텍처 특강", startDateTime, "김항해 강사님의 아키텍처 특강입니다.", 30, "김항해");
        lectureRepository.save(lecture);

        // when
        List<Lecture> lectures = lectureRepository.findByStartDateTimeAfterOrderByStartDateTime(standardDateTime);

        // then
        assertThat(lectures).hasSize(0);
    }

    @Test
    void 기준_일자_시간_이후에_시작하는_신청_가능한_강의_목록이_강의_시작_일자_시간_기준_오름차순으로_정렬되어_조회된다() {
        // given
        LocalDateTime standardDateTime = LocalDateTime.of(2024, 12, 26, 10, 0, 0);

        LocalDateTime startDateTime1 = LocalDateTime.of(2024, 12, 28, 10, 0, 0);
        LocalDateTime startDateTime2 = LocalDateTime.of(2024, 12, 28, 9, 0);
        LocalDateTime startDateTime3 = LocalDateTime.of(2024, 12, 28, 11, 0, 0);

        Lecture lecture1 = createLecture("강의1", startDateTime1, "설명1", 30, "강사1");
        Lecture lecture2 = createLecture("강의2", startDateTime2, "설명2", 29, "강사2");
        Lecture lecture3 = createLecture("강의3", startDateTime3, "설명3", 20, "강사3");

        lectureRepository.saveAll(List.of(lecture1, lecture2, lecture3));

        // when
        List<Lecture> results = lectureRepository.findByStartDateTimeAfterOrderByStartDateTime(standardDateTime);

        // then
        assertThat(results).hasSize(3)
                .extracting("title", "startDateTime", "description", "remainingCapacity", "lecturerName")
                .containsExactly(
                        tuple(lecture2.getTitle(), lecture2.getStartDateTime(), lecture2.getDescription(), lecture2.getRemainingCapacity(), lecture2.getLecturerName()),
                        tuple(lecture1.getTitle(), lecture1.getStartDateTime(), lecture1.getDescription(), lecture1.getRemainingCapacity(), lecture1.getLecturerName()),
                        tuple(lecture3.getTitle(), lecture3.getStartDateTime(), lecture3.getDescription(), lecture3.getRemainingCapacity(), lecture3.getLecturerName())
                );
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