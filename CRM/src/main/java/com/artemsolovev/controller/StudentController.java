package com.artemsolovev.controller;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.Student;
import com.artemsolovev.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private StudentService studentService;
    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(path = "/{idTeacher}")
    public ResponseEntity<ResponseResult<Student>> add(@RequestBody Student student, @PathVariable long idTeacher) {
        try {
            this.studentService.add(student, idTeacher);
            return new ResponseEntity<>(new ResponseResult<>(null, student), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Student>> get(@PathVariable long id) {
        try {
            Student student = this.studentService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, student), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseResult<List<Student>>> get() {
        List<Student> list = this.studentService.get();
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }

    @GetMapping(path = "/all/{idTeacher}")
    public ResponseEntity<ResponseResult<List<Student>>> getByTeacher(@PathVariable long idTeacher) {
        List<Student> list = this.studentService.getByTeacher(idTeacher);
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseResult<Student>> update(@RequestBody Student student) {
        try {
            if (student.getId() <= 0) {
                return new ResponseEntity<>(new ResponseResult<>("Incorrect format id", null),
                        HttpStatus.BAD_REQUEST);
            }
            Student studentNew = this.studentService.update(student);
            return new ResponseEntity<>(new ResponseResult<>(null, studentNew), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
