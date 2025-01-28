package com.artemsolovev.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "trainings",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"apprentice_id", "date"})})
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(nullable = false)
    @Positive(message = "Number must be positive")
    private int numberGym;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "apprentice_id", nullable = false)
    private Apprentice apprentice;

    @NonNull
    @JsonFormat(pattern = "dd.MM.yyyy", shape = JsonFormat.Shape.STRING)
    @FutureOrPresent(message = "Training date must be in future")
    private LocalDate date;

    @NonNull
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime timeStart;
}
