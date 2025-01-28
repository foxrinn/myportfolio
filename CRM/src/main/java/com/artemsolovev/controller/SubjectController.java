package com.artemsolovev.controller;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.Subject;
import com.artemsolovev.service.StudentService;
import com.artemsolovev.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {
    private SubjectService subjectService;

    @Autowired
    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping(path = "/{idTeacher}")
    public ResponseEntity<ResponseResult<Subject>> add(@RequestBody Subject subject, @PathVariable long idTeacher) {
        try {
            this.subjectService.add(idTeacher, subject);
            return new ResponseEntity<>(new ResponseResult<>(null, subject), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/all/{idTeacher}")
    public ResponseEntity<ResponseResult<List<Subject>>> getAll(@PathVariable long idTeacher) {
        List<Subject> list = this.subjectService.getAll(idTeacher);
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Subject>> get(@PathVariable long id) {
        try {
            Subject subject = this.subjectService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, subject), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Subject>> delete(@PathVariable long id) {
        try {
            Subject subject = this.subjectService.delete(id);
            return new ResponseEntity<>(new ResponseResult<>(null, subject), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
