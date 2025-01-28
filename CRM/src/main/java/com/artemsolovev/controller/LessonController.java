package com.artemsolovev.controller;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.Lesson;
import com.artemsolovev.model.Teacher;
import com.artemsolovev.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/lesson")
public class LessonController {
    private LessonService lessonService;
    @Autowired
    public void setLessonService(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping
    public ResponseEntity<ResponseResult<Lesson>> add(@RequestBody Lesson lesson,
                                                      @RequestParam long idTeacher, @RequestParam long idStudent,
                                                      @RequestParam long idSubject) {
        try {
            this.lessonService.add(lesson, idTeacher, idStudent, idSubject);
            return new ResponseEntity<>(new ResponseResult<>(null, lesson), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Lesson>> get(@PathVariable long id) {
        try {
            Lesson lesson = this.lessonService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, lesson), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/student/{id}")
    public ResponseEntity<ResponseResult<List<Lesson>>> getByStudent(@PathVariable long id) {
        List<Lesson> list = this.lessonService.getByStudent(id);
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseResult<List<Lesson>>> getByTeacher(@RequestParam long idTeacher) {
        List<Lesson> list = this.lessonService.getByTeacher(idTeacher);
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }

    @GetMapping(path = "/date")
    public ResponseEntity<ResponseResult<List<LocalTime>>> getByDate(@RequestParam long idTeacher,
                                                                     @RequestParam
                                                                     @DateTimeFormat(pattern = "dd.MM.yyyy")
                                                                     LocalDate localDate) {
        try {
            List<LocalTime> localTimeList = this.lessonService.getByDate(idTeacher, localDate);
            return new ResponseEntity<>(new ResponseResult<>(null, localTimeList), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Lesson>> delete(@PathVariable long id) {
        try {
            Lesson lesson = this.lessonService.delete(id);
            return new ResponseEntity<>(new ResponseResult<>(null, lesson), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}

