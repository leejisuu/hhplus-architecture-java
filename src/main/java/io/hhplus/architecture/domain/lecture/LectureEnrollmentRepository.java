package io.hhplus.architecture.domain.lecture;

import java.util.List;

public interface LectureEnrollmentRepository {

    LectureEnrollment save(LectureEnrollment lectureEnrollment);

    List<LectureEnrollment> findAllByUserId(Long userId);

    LectureEnrollment findByUserIdAndLectureId(long userId, long lectureId);
}
