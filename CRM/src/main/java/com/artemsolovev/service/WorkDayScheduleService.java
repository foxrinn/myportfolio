package com.artemsolovev.service;

import com.artemsolovev.model.WorkDaySchedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface WorkDayScheduleService {
    void add(long idTeacher, WorkDaySchedule workDaySchedule);
    WorkDaySchedule delete(long id);
    WorkDaySchedule get(long id);
    WorkDaySchedule getByDate(LocalDate localDate, long idTeacher);
    List<WorkDaySchedule> getAllByTeacher(long idTeacher);

}
