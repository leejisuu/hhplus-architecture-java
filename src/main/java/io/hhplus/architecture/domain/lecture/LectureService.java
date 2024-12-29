package io.hhplus.architecture.domain.lecture;

import io.hhplus.architecture.interfaces.api.lecture.dto.LectureEnrollmentRequest;
import io.hhplus.architecture.interfaces.api.lecture.dto.LectureEnrollmentResponse;
import io.hhplus.architecture.interfaces.api.lecture.dto.LectureResponse;
import io.hhplus.architecture.support.exception.CustomException;
import io.hhplus.architecture.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LectureService {

    private final LectureRepository lectureRepository;
    private final LectureEnrollmentRepository lectureEnrollmentRepository;

    // 특강 신청 가능 목록 조회
    public List<LectureResponse> getLectures(LocalDateTime currentDateTime) {
        List<Lecture> lectures = lectureRepository.findByStartDateTimeAfterOrderByStartDateTime(currentDateTime);

        return lectures.stream()
                        .map(LectureResponse::of)
                        .collect(Collectors.toList());
    }

    // 특강 신청
    @Transactional
    public LectureEnrollmentResponse enrollLecture(Long userId, Long lectureId) {
        // Lecture를 트랜잭션 초입에 비관적 락 활용해서 조회
        Lecture lecture = lectureRepository.findByIdWithLock(lectureId);

        // 이미 수강 신청 한 특강인지 체크
        LectureEnrollment enrolledLecture = lectureEnrollmentRepository.findByUserIdAndLectureId(userId, lecture.getId());
        if(enrolledLecture != null) {
            throw new CustomException(ErrorCode.LECTURE_ALREADY_ENROLLED);
        }

        // 신청 가능 잔여 좌석이 1보다 크면 1 감소. 0 이하라면 예외 발생
        lecture.deductRemainingCapacity();

        // lecture 스냅샷 정보 세팅
        LectureEnrollment lectureEnrollment = LectureEnrollment.create(lecture, userId);

        return LectureEnrollmentResponse.of(lectureEnrollmentRepository.save(lectureEnrollment));
    }

    // 특강 신청 완료 목록 조회
    public List<LectureEnrollmentResponse> getEnrolledLectures(Long userId) {
        List<LectureEnrollment> enrolledLectures = lectureEnrollmentRepository.findAllByUserIdOrderByCreatedAtDesc(userId);

        return enrolledLectures.stream()
                .map(LectureEnrollmentResponse::of)
                .collect(Collectors.toList());
    }
}
