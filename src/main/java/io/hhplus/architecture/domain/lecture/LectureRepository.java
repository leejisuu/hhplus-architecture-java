package io.hhplus.architecture.domain.lecture;

import java.time.LocalDateTime;
import java.util.List;

public interface LectureRepository {

    List<Lecture> findByStartDateTimeAfterOrderByStartDateTime(LocalDateTime startDateTime);

    Lecture save(Lecture lecture);

    // TODO : deleteAllInBatch 삭제하기
    void deleteAllInBatch();

    List<Lecture> saveAll(List<Lecture> lectures);

    Lecture findByIdWithLock(Long lectureId);

    Lecture saveAndFlush(Lecture lecture);
}
