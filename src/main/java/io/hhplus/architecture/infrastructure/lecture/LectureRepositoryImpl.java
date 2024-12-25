package io.hhplus.architecture.infrastructure.lecture;

import io.hhplus.architecture.domain.lecture.Lecture;
import io.hhplus.architecture.domain.lecture.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public List<Lecture> findByStartDateTimeAfter(LocalDateTime startDateTime) {
        return lectureJpaRepository.findByStartDateTimeAfter(startDateTime);
    }

    @Override
    public Lecture findById(long lectureId) {
        return lectureJpaRepository.findById(lectureId).orElse(null);
    }

    @Override
    public void save(Lecture lecture) {
        lectureJpaRepository.save(lecture);
    }
}
