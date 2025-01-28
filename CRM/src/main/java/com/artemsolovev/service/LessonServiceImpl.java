package com.artemsolovev.service;

import com.artemsolovev.model.Lesson;
import com.artemsolovev.model.Subject;
import com.artemsolovev.model.Teacher;
import com.artemsolovev.model.WorkDaySchedule;
import com.artemsolovev.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {
    private LessonRepository lessonRepository;
    private TeacherService teacherService;
    private StudentService studentService;
    private SubjectService subjectService;
    private WorkDayScheduleService workDayScheduleService;
    @Autowired
    public void setLessonRepository(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }
    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
    @Autowired
    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    @Autowired
    public void setWorkDayScheduleService(WorkDayScheduleService workDayScheduleService) {
        this.workDayScheduleService = workDayScheduleService;
    }

    public static boolean isIncluding(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return (start1.isAfter(start2) || start1.equals(start2)) && (end1.isBefore(end2) || end1.equals(end2));
    }

    public boolean isWorking(long idTeacher, LocalDate localDate, LocalTime localTime) {
        LocalTime[] localTimes = this.workDayScheduleService.getByDate(localDate, idTeacher).get();
        return isIncluding(localTime, localTime.plusMinutes(90), localTimes[0], localTimes[1]);
    }

   /* @Override
    public void add(Lesson lesson, long idTeacher, long idStudent) {
        try {
            if (!isWorking(idTeacher, lesson.getDate().getDayOfWeek().name().toLowerCase(), lesson.getTimeStart()))
                throw new IllegalArgumentException("Lesson couldn't be added!");
            lesson.setTeacher(this.teacherService.get(idTeacher));
            lesson.setStudent(this.studentService.get(idStudent));
            this.lessonRepository.save(lesson);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Lesson has already added!");
        }
    }*/


    @Override
    public void add(Lesson lesson, long idTeacher, long idStudent, long idSubject) {
        try {
            if (!isWorking(idTeacher, lesson.getDate(), lesson.getTimeStart()))
                throw new IllegalArgumentException("Lesson couldn't be added!");
            lesson.setTeacher(teacherService.get(idTeacher));
            lesson.setStudent(studentService.get(idStudent));
            lesson.setSubject(subjectService.get(idSubject));
            this.lessonRepository.save(lesson);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Lesson has already added!");
        }
    }

    @Override
    public Lesson get(long id) {
        return this.lessonRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Lesson does not exists!"));
    }

    @Override
    public List<Lesson> getByStudent(long idStudent) {
        return this.lessonRepository.findAllByStudent_Id(idStudent);
    }

    @Override
    public List<Lesson> getByTeacher(long idTeacher) {
        return this.lessonRepository.findAllByTeacher_Id(idTeacher);
    }

    @Override
    public Lesson delete(long id) {
        Lesson lesson = get(id);
        this.lessonRepository.deleteById(id);
        return lesson;
    }

    @Override
    public List<LocalTime> getByDate(long idTeacher, LocalDate localDate) {
        WorkDaySchedule workDaySchedule = workDayScheduleService.getByDate(localDate, idTeacher);
        LocalTime[] localTimes = workDaySchedule.get();
        List<LocalTime> localTimeList = new ArrayList<>();
        List<Lesson> allByTeacherId = lessonRepository.findAllByTeacher_Id(idTeacher);
        allByTeacherId.sort(new Comparator<Lesson>() {
            @Override
            public int compare(Lesson o1, Lesson o2) {
                return o1.getTimeStart().compareTo(o2.getTimeStart());
            }
        });
        int count = 0;
        for (LocalTime i = localTimes[0]; i.isBefore(localTimes[1].minusMinutes(89)); i = i.plusMinutes(30)) {
            for (Lesson lesson : allByTeacherId) {
                if (lesson.getDate().equals(localDate) && lesson.getTimeStart().equals(i)) {
                    localTimeList.remove(i.minusMinutes(30));
                    localTimeList.remove(i.minusMinutes(60));
                    i = i.plusMinutes(90);
                }
            }
            localTimeList.add(i);
        }
        return localTimeList;
    }
}
