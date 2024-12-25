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
import java.util.stream.Collectors;

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
                .collect(Collectors.toList());
    }

    // 특강 신청 API
    @PostMapping("/lectures/enroll")
    public LectureEnrollmentResponse enrollLecture(@RequestBody LectureEnrollmentRequest lectureEnrollmentRequest) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LectureEnrollment lectureEnrollment = lectureService.saveLectureEnrollment(lectureEnrollmentRequest.getUserId(), lectureEnrollmentRequest.getLectureId(), currentDateTime);

        return LectureEnrollmentResponse.of(lectureEnrollment);
    }

    // 특강 신청 완료 목록 조회 API
    @GetMapping("/lectures/enrolled/{userid}")
    public List<LectureEnrollmentResponse> getEnrolledLectures(@PathVariable Long userid) {
        List<LectureEnrollment> enrolledLectures = lectureService.getEnrolledLectures(userid);

        return enrolledLectures.stream()
                .map(LectureEnrollmentResponse::of)
                .collect(Collectors.toList());
    }
}
