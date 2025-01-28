package com.artemsolovev.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(nullable = false)
    private boolean payment;

    @NonNull
    @Column(nullable = false)
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @NonNull
    @Column(nullable = false)
    private String theme;

    @NonNull
    @Column(nullable = false)
    private String homework;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @NonNull
    @JsonFormat(pattern = "dd.MM.yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate date;

    @NonNull
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime timeStart;
}
