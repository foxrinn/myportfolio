package com.artemsolovev.controller;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.Answer;
import com.artemsolovev.model.User;
import com.artemsolovev.model.UserDetailsImpl;
import com.artemsolovev.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    private AnswerService answerService;

    @Autowired
    public void setAnswerService(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping(path = "/{idResult}")
    public ResponseEntity<ResponseResult<Answer>> add(@RequestBody Answer answer,
                                                      @PathVariable long idResult, Authentication authentication) {
        try {
            if (authentication != null && authentication.isAuthenticated()) {
                long id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
                this.answerService.add(answer, idResult, id);
                return new ResponseEntity<>(new ResponseResult<>(null, answer), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseResult<>(null, null), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseResult<List<Answer>>> get(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            long id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
            List<Answer> list = this.answerService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseResult<>(null, null), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseResult<Answer>> get(@PathVariable long id) {
        try {
            Answer answer = this.answerService.getByResult(id);
            return new ResponseEntity<>(new ResponseResult<>(null, answer), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
