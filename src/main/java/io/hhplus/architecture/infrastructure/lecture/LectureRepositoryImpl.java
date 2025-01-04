package io.hhplus.architecture.infrastructure.lecture;

import io.hhplus.architecture.domain.lecture.Lecture;
import io.hhplus.architecture.domain.lecture.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public List<Lecture> findByStartDateTimeAfterOrderByStartDateTime(LocalDateTime startDateTime) {
        return lectureJpaRepository.findByStartDateTimeAfterOrderByStartDateTime(startDateTime);
    }

    @Override
    public Lecture save(Lecture lecture) {
        return lectureJpaRepository.save(lecture);
    }

    @Override
    public void deleteAllInBatch() {
        lectureJpaRepository.deleteAllInBatch();
    }

    @Override
    public List<Lecture> saveAll(List<Lecture> lectures) {
        return lectureJpaRepository.saveAll(lectures);
    }

    @Override
    public Lecture findByIdWithPessimisticLock(Long lectureId) {
        return lectureJpaRepository.findByIdWithPessimisticLock(lectureId);
    }

    @Override
    public Lecture saveAndFlush(Lecture lecture) {
        return lectureJpaRepository.saveAndFlush(lecture);
    }
}
