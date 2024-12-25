package io.hhplus.architecture.domain.lecture;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LectureService {

    private final LectureRepository lectureRepository;
    private final LectureEnrollmentRepository lectureEnrollmentRepository;

    public List<Lecture> getLectures(LocalDateTime startDateTime) {
        return lectureRepository.findByStartDateTimeAfter(startDateTime);
    }

    public List<LectureEnrollment> getEnrolledLectures(Long userId) {
        return lectureEnrollmentRepository.findAllByUserId(userId);
    }

    @Transactional
    public LectureEnrollment saveLectureEnrollment(long userId, long lectureId, LocalDateTime currentDateTime) {
        Lecture lecture = lectureRepository.findById(lectureId);

        lecture.deductRemainingCapacity();
        lectureRepository.save(lecture);

        LectureEnrollment lectureEnrollment = LectureEnrollment.create(lecture, userId, currentDateTime);

        return lectureEnrollmentRepository.save(lectureEnrollment);
    }
}
