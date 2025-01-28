package com.artemsolovev.service;

import com.artemsolovev.model.Lesson;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface LessonService {
    void add(Lesson lesson, long idTeacher, long idStudent, long idSubject);
    Lesson get(long id);
    List<Lesson> getByStudent(long idStudent);
    List<Lesson> getByTeacher(long idTeacher);
    Lesson delete(long id);
    List<LocalTime> getByDate(long idTeacher, LocalDate localDate);
}
