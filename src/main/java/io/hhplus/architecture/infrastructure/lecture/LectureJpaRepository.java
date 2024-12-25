package io.hhplus.architecture.infrastructure.lecture;

import io.hhplus.architecture.domain.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByStartDateTimeAfter(LocalDateTime startDateTime);
}
