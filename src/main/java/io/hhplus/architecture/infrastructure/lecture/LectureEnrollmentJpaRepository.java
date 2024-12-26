package io.hhplus.architecture.infrastructure.lecture;

import io.hhplus.architecture.domain.lecture.LectureEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureEnrollmentJpaRepository extends JpaRepository<LectureEnrollment, Long> {

    LectureEnrollment findByUserIdAndLectureId(long userId, long lectureId);

    List<LectureEnrollment> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
