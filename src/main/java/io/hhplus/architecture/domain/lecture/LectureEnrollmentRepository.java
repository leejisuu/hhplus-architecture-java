package io.hhplus.architecture.domain.lecture;

import java.util.List;

public interface LectureEnrollmentRepository {

    LectureEnrollment save(LectureEnrollment lectureEnrollment);

    List<LectureEnrollment> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    LectureEnrollment findByUserIdAndLectureId(long userId, long lectureId);

    void deleteAllInBatch();

    List<LectureEnrollment> saveAll(List<LectureEnrollment> lectureEnrollments);
}
