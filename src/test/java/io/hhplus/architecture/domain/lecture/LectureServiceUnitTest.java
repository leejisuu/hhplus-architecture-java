package io.hhplus.architecture.domain.lecture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LectureServiceUnitTest {

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureService lectureService;

    @Test
    void 기준_일자_시간_이후에_시작하는_신청_가능한_강의_목록이_강의_시작_일자_시간_기준_오름차순으로_정렬되어_조회된다() {
        // given
        LocalDateTime standardDateTime = LocalDateTime.of(2024, 12, 26, 10, 0, 0);

        LocalDateTime startDateTime1 = LocalDateTime.of(2024, 12, 28, 9, 0, 0);
        LocalDateTime startDateTime2 = LocalDateTime.of(2024, 12, 28, 10, 0, 0);
        LocalDateTime startDateTime3 = LocalDateTime.of(2024, 12, 28, 13, 0, 0);

        given(lectureRepository.findByStartDateTimeAfterOrderByStartDateTime(standardDateTime))
                .willReturn(List.of(
                        createLecture("강의1", startDateTime1, "설명1", 30, "강사1"),
                        createLecture("강의2", startDateTime2, "설명2", 29, "강사2"),
                        createLecture("강의3", startDateTime3, "설명3", 20, "강사3")
                ));

        // when // then
        assertThat(lectureService.getLectures(standardDateTime)).hasSize(3)
                .extracting("title", "startDateTime", "description", "remainingCapacity", "lecturerName")
                .containsExactly(
                        tuple("강의1", startDateTime1, "설명1", 30, "강사1"),
                        tuple("강의2", startDateTime2, "설명2", 29, "강사2"),
                        tuple("강의3", startDateTime3, "설명3", 20, "강사3")
                );

        verify(lectureRepository, times(1)).findByStartDateTimeAfterOrderByStartDateTime(standardDateTime);
    }

    private static Lecture createLecture(String title, LocalDateTime startDateTime, String description, int remainingCapacity, String lecturerName) {
        return Lecture.builder()
                .title(title)
                .startDateTime(startDateTime)
                .description(description)
                .remainingCapacity(remainingCapacity)
                .lecturerName(lecturerName)
                .build();
    }
}
