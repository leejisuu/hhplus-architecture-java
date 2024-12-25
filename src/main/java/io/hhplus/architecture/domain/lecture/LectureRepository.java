package io.hhplus.architecture.domain.lecture;

import java.time.LocalDateTime;
import java.util.List;

public interface LectureRepository {

    List<Lecture> findByStartDateTimeAfter(LocalDateTime startDateTime);

    Lecture findById(long lectureId);

    void save(Lecture lecture);
}
