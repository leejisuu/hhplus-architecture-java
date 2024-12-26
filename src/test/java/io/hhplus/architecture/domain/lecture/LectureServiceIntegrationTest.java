package io.hhplus.architecture.domain.lecture;

import io.hhplus.architecture.interfaces.api.lecture.dto.LectureEnrollmentRequest;
import io.hhplus.architecture.interfaces.api.lecture.dto.LectureEnrollmentResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
        LocalDateTime startDateTime = LocalDateTime.of(2024, 12, 28, 10, 0, 0);

        Lecture lecture = lectureRepository.save(createLecture("특강", startDateTime, "설명", 1, "강사"));

        LectureEnrollmentRequest request = LectureEnrollmentRequest.builder()
                .userId(userId)
                .lectureId(lecture.getId())
                .build();

        // when
        LectureEnrollmentResponse response = lectureService.saveLectureEnrollment(request);

        // then
        assertThat(response).isNotNull()
                .extracting("userId", "lectureId", "title", "startDateTime", "description", "lecturerName")
                .containsExactly(userId, lecture.getId(), lecture.getTitle(), lecture.getStartDateTime(), lecture.getDescription(), lecture.getLecturerName());
    }

    @Test
    void 신청_기능한_잔여_좌석수가_0_이하면_예외를_발생하고_신청_실패한다() {
        // given
        Long userId = 1L;
        LocalDateTime startDateTime = LocalDateTime.of(2024, 12, 28, 10, 0, 0);

        Lecture lecture = lectureRepository.save(createLecture("특강", startDateTime, "설명", 0, "강사"));

        LectureEnrollmentRequest request = LectureEnrollmentRequest.builder()
                .userId(userId)
                .lectureId(lecture.getId())
                .build();

        // when // then
        assertThatThrownBy(() -> lectureService.saveLectureEnrollment(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("신청 가능한 최대 정원이 초과되었습니다.");

    }

    @Test
    void 동시에_동일한_특강에_대해_40명이_신청했을_때_30명만_성공한다() throws InterruptedException {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2024, 12, 28, 10, 0, 0);
        Lecture lecture = lectureRepository.save(createLecture("특강", startDateTime, "설명", 30, "강사"));
        System.out.println(lecture.getId() + " ");

        // 성공 카운트를 세는 변수
        AtomicInteger cnt = new AtomicInteger();
        // 예외 발생 여부 체크하는 변수
        AtomicBoolean execptionThrown = new AtomicBoolean(false);

        int threadCount = 40;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 1; i <= threadCount; i++) {
            // 예외 발생하면 반복문 빠져나오기
            if(execptionThrown.get()) {
                break;
            }

            Long userId = Long.valueOf(i);
            executorService.submit(() -> {
                try {
                    LectureEnrollmentRequest request = LectureEnrollmentRequest.builder()
                            .userId(userId)
                            .lectureId(lecture.getId())
                            .build();

                    lectureService.saveLectureEnrollment(request);
                    cnt.getAndIncrement();
                } catch(RuntimeException e) {
                    execptionThrown.set(true);
                } finally {
                    countDownLatch.countDown();
                }

            });
        }

        countDownLatch.await();
        executorService.shutdown();

        // when // then
        assertThat(cnt.get()).isEqualTo(30);
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
