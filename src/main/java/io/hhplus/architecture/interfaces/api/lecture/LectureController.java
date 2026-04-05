package io.hhplus.architecture.interfaces.api.lecture;

import io.hhplus.architecture.domain.lecture.Lecture;
import io.hhplus.architecture.domain.lecture.LectureEnrollment;
import io.hhplus.architecture.domain.lecture.LectureService;
import io.hhplus.architecture.interfaces.api.lecture.dto.LectureEnrollmentRequest;
import io.hhplus.architecture.interfaces.api.lecture.dto.LectureEnrollmentResponse;
import io.hhplus.architecture.interfaces.api.lecture.dto.LectureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class LectureController {

    private final LectureService lectureService;

    // 특강 신청 가능 목록 조회 API
    @GetMapping("/lectures")
    public List<LectureResponse> getLectures() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<Lecture> lectures = lectureService.getLectures(currentDateTime);
        return lectures.stream()
                .map(LectureResponse::of)
                .toList();
    }

    // 특강 신청 API
    @PostMapping("/lectures/enroll")
    public LectureEnrollmentResponse enrollLecture(@RequestBody LectureEnrollmentRequest lectureEnrollmentRequest) {
        LectureEnrollment lectureEnrollment = lectureService.enrollLecture(lectureEnrollmentRequest.getUserId(), lectureEnrollmentRequest.getLectureId());
        return LectureEnrollmentResponse.of(lectureEnrollment);
    }

    // 특강 신청 완료 목록 조회 API
    @GetMapping("/lectures/enrolled/{userid}")
    public List<LectureEnrollmentResponse> getEnrolledLectures(@PathVariable Long userid) {
        List<LectureEnrollment> lectureEnrollments = lectureService.getEnrolledLectures(userid);
        return lectureEnrollments.stream()
                .map(LectureEnrollmentResponse::of)
                .toList();
    }
}
