package com.artemsolovev.controller;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.Teacher;
import com.artemsolovev.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private TeacherService teacherService;

    @Autowired
    public void setTrainerService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    public ResponseEntity<ResponseResult<Teacher>> add(@RequestBody Teacher teacher) {
        try {
            this.teacherService.add(teacher);
            return new ResponseEntity<>(new ResponseResult<>(null, teacher), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Teacher>> get(@PathVariable long id) {
        try {
            Teacher teacher = this.teacherService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, teacher), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseResult<List<Teacher>>> get() {
        List<Teacher> list = this.teacherService.get();
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseResult<Teacher>> update(@RequestBody Teacher teacher) {
        try {
            if (teacher.getId() <= 0) {
                return new ResponseEntity<>(new ResponseResult<>("Incorrect format id", null),
                        HttpStatus.BAD_REQUEST);
            }
            Teacher teacherNew = this.teacherService.update(teacher);
            return new ResponseEntity<>(new ResponseResult<>(null, teacherNew), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
