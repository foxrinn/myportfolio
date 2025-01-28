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
@Table(name = "work_day_schedules")
public class WorkDaySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @NonNull
    @JsonFormat(pattern = "dd.MM.yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate date;

    @NonNull
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime start;

    @NonNull
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime end;

    public LocalTime[] get() {
        try {
            LocalTime[] mass = new LocalTime[2];
            mass[0] = (LocalTime) this.getClass().getDeclaredField("start").get(this);
            mass[1] = (LocalTime) this.getClass().getDeclaredField("end").get(this);
            return mass;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("Couldn't get day in Teacher Schedule!");
        }
    }
}
