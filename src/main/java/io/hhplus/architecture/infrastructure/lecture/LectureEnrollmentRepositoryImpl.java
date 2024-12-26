package io.hhplus.architecture.infrastructure.lecture;

import io.hhplus.architecture.domain.lecture.LectureEnrollment;
import io.hhplus.architecture.domain.lecture.LectureEnrollmentRepository;
import io.hhplus.architecture.domain.lecture.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class LectureEnrollmentRepositoryImpl implements LectureEnrollmentRepository {

    private final LectureEnrollmentJpaRepository lectureEnrollmentJpaRepository;

    @Override
    public LectureEnrollment save(LectureEnrollment lectureEnrollment) {
        return lectureEnrollmentJpaRepository.save(lectureEnrollment);
    }

    @Override
    public List<LectureEnrollment> findAllByUserIdOrderByCreatedAtDesc(Long userId) {
        return lectureEnrollmentJpaRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public LectureEnrollment findByUserIdAndLectureId(long userId, long lectureId) {
        return lectureEnrollmentJpaRepository.findByUserIdAndLectureId(userId, lectureId);
    }

    @Override
    public void deleteAllInBatch() {
        lectureEnrollmentJpaRepository.deleteAllInBatch();
    }

    @Override
    public List<LectureEnrollment> saveAll(List<LectureEnrollment> lectureEnrollments) {
        return lectureEnrollmentJpaRepository.saveAll(lectureEnrollments);
    }
}
