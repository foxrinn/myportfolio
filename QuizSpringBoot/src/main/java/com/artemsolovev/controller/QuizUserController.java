package com.artemsolovev.controller;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.Answer;
import com.artemsolovev.model.QuizUser;
import com.artemsolovev.model.UserDetailsImpl;
import com.artemsolovev.service.QuizUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizuser")
public class QuizUserController {

    private QuizUserService quizUserService;

    @Autowired
    public void setQuizUserService(QuizUserService quizUserService) {
        this.quizUserService = quizUserService;
    }

    @PostMapping
    public ResponseEntity<ResponseResult<QuizUser>> add(@RequestBody QuizUser quizUser) {
        try {
            this.quizUserService.add(quizUser);
            return new ResponseEntity<>(new ResponseResult<>(null, quizUser), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseResult<List<QuizUser>>> get(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            List<QuizUser> list = this.quizUserService.get();
            return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseResult<>(null, null), HttpStatus.BAD_REQUEST);
    }
}
