package com.artemsolovev.crmclient.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Lesson {
    private long id;

    @NonNull
    private boolean payment;

    @NonNull
    private boolean status;

    private Subject subject;

    @NonNull
    private String theme;

    @NonNull
    private String homework;

    private Teacher teacher;

    private Student student;
    @NonNull
    @JsonFormat(pattern = "dd.MM.yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate date;
    @NonNull
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime timeStart;

    @Override
    public String toString() {
        return "Дата: " + date +
                ", Начало занятия: " + timeStart +
                ", Предмет: " + subject.getName() + ".";
    }
}
