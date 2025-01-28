package com.artemsolovev.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "trainer_schedules")
public class TrainerSchedule {
    @Id
    @Column(name = "trainer_id")
    private long id;
    @ToString.Exclude
    @OneToOne
    @NonNull
    @MapsId
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
    @NonNull
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime mondayStart;
    @NonNull
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime mondayEnd;
    @NonNull
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime tuesdayStart;
    @NonNull
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime tuesdayEnd;
    @NonNull
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime wednesdayStart;
    @NonNull
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime wednesdayEnd;
    @NonNull
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime thursdayStart;
    @NonNull
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime thursdayEnd;
    @NonNull
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime fridayStart;
    @NonNull
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime fridayEnd;
    @NonNull
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime saturdayStart;
    @NonNull
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime saturdayEnd;
    @NonNull
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime sundayStart;
    @NonNull
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime sundayEnd;

    public TrainerSchedule(@NonNull Trainer trainer) {
        this.trainer = trainer;
    }

    public void set(String day, LocalTime start, LocalTime end) {
        try {
            this.getClass().getDeclaredField(day + "Start").set(this, start);
            this.getClass().getDeclaredField(day + "End").set(this, end);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalArgumentException("Couldn't update day in Trainer Schedule!");
        }
    }

    public LocalTime[] get(String day) {
        try {
            LocalTime[] mass = new LocalTime[2];
            mass[0] = (LocalTime) this.getClass().getDeclaredField(day + "Start").get(this);
            mass[1] = (LocalTime) this.getClass().getDeclaredField(day + "End").get(this);
            return mass;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("Couldn't get day in Trainer Schedule!");
        }
    }
}
