package io.hhplus.architecture.interfaces.api.lecture;

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
        return lectureService.getLectures(currentDateTime);
    }

    // 특강 신청 API
    @PostMapping("/lectures/enroll")
    public LectureEnrollmentResponse enrollLecture(@RequestBody LectureEnrollmentRequest lectureEnrollmentRequest) {
        return lectureService.enrollLecture(lectureEnrollmentRequest.getUserId(), lectureEnrollmentRequest.getLectureId());
    }

    // 특강 신청 완료 목록 조회 API
    @GetMapping("/lectures/enrolled/{userid}")
    public List<LectureEnrollmentResponse> getEnrolledLectures(@PathVariable Long userid) {
        return lectureService.getEnrolledLectures(userid);
    }
}
