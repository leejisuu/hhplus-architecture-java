package io.hhplus.architecture.domain.lecture;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate date;

    private LocalTime startTime;

    private String description;

    private int maxCapacity;

    private String lectureName;

    @OneToMany(mappedBy = "user")
    private List<LectureEnrollment> enrollments = new ArrayList<>();
}
