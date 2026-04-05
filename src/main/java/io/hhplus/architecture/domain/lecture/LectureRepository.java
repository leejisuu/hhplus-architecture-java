package io.hhplus.architecture.domain.lecture;

import java.time.LocalDateTime;
import java.util.List;

public interface LectureRepository {

    List<Lecture> findByStartDateTimeAfterOrderByStartDateTime(LocalDateTime startDateTime);

    Lecture save(Lecture lecture);

    List<Lecture> saveAll(List<Lecture> lectures);

    Lecture findByIdWithLock(Long lectureId);

}
