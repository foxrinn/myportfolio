package com.artemsolovev.service;

import com.artemsolovev.model.WorkDaySchedule;
import com.artemsolovev.repository.WorkDayScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class WorkDayScheduleServiceImpl implements WorkDayScheduleService {

    private WorkDayScheduleRepository workDayScheduleRepository;
    private TeacherService teacherService;

    @Autowired
    public void setTrainerScheduleRepository(WorkDayScheduleRepository workDayScheduleRepository){
        this.workDayScheduleRepository = workDayScheduleRepository;
    }

    @Autowired
    public void setTrainerService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    public void add(long idTeacher, WorkDaySchedule workDaySchedule) {
        try {
            workDaySchedule.setTeacher(teacherService.get(idTeacher));
            this.workDayScheduleRepository.save(workDaySchedule);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Work day has already added!");
        }
    }

    @Override
    public WorkDaySchedule get(long id) {
        return this.workDayScheduleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Work day does not exists!"));
    }

    @Override
    public WorkDaySchedule getByDate(LocalDate localDate, long idTeacher) {
        return this.workDayScheduleRepository.findByDateAndTeacher_Id(localDate, idTeacher).orElseThrow(
                () -> new IllegalArgumentException("Work day does not exists!"));
    }


    @Override
    public WorkDaySchedule delete(long id) {
        WorkDaySchedule workDaySchedule = get(id);
        this.workDayScheduleRepository.deleteById(id);
        return workDaySchedule;
    }

    @Override
    public List<WorkDaySchedule> getAllByTeacher(long idTeacher) {
        return this.workDayScheduleRepository.findAllByTeacher_Id(idTeacher);
    }


}
