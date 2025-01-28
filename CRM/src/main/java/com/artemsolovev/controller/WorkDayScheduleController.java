package com.artemsolovev.controller;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.WorkDaySchedule;
import com.artemsolovev.service.WorkDayScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/workdayschedule")
public class WorkDayScheduleController {
    private WorkDayScheduleService workDayScheduleService;

    @Autowired
    public void setWorkDayScheduleService(WorkDayScheduleService teacherScheduleService) {
        this.workDayScheduleService = teacherScheduleService;
    }

    @PostMapping(path = "/{idTeacher}")
    public ResponseEntity<ResponseResult<WorkDaySchedule>> add(@RequestBody WorkDaySchedule workDaySchedule, @PathVariable long idTeacher) {
        try {
            this.workDayScheduleService.add(idTeacher, workDaySchedule);
            return new ResponseEntity<>(new ResponseResult<>(null, workDaySchedule), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<WorkDaySchedule>> get(@PathVariable long id) {
        try {
            WorkDaySchedule teacherSchedule = this.workDayScheduleService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, teacherSchedule), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/teacher/{idTeacher}")
    public ResponseEntity<ResponseResult<List<WorkDaySchedule>>> getAllByTeacher(@PathVariable long idTeacher) {
        List<WorkDaySchedule> list = this.workDayScheduleService.getAllByTeacher(idTeacher);
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<WorkDaySchedule>> delete(@PathVariable long id) {
        try {
            WorkDaySchedule teacherSchedule = this.workDayScheduleService.delete(id);
            return new ResponseEntity<>(new ResponseResult<>(null, teacherSchedule), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
