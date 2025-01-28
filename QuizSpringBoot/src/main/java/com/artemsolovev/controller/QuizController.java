package com.artemsolovev.controller;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.model.Quiz;
import com.artemsolovev.model.User;
import com.artemsolovev.model.UserDetailsImpl;
import com.artemsolovev.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private QuizService quizService;

    @Autowired
    public void setQuizService(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping(path = "/{amount}/{category}/{difficulty}/{idUser}")
    public ResponseEntity<ResponseResult<Quiz>> get(Authentication authentication, @PathVariable int amount,
                                                    @PathVariable int category, @PathVariable String difficulty,
                                                    @PathVariable long idUser) {
        try {
            if (authentication != null && authentication.isAuthenticated()) {
                Quiz quiz = this.quizService.add(amount, category, difficulty, idUser);
                return new ResponseEntity<>(new ResponseResult<>(null, quiz), HttpStatus.OK);
            }
            return new ResponseEntity<>(
                    new ResponseResult<>("User not found", null), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{idUser}")
    public ResponseEntity<ResponseResult<List<Quiz>>> get(@PathVariable long idUser, Authentication authentication) {
        try {
            if (authentication != null && authentication.isAuthenticated()) {
                List<Quiz> quizzes = this.quizService.get(idUser);
                return new ResponseEntity<>(new ResponseResult<>(null, quizzes), HttpStatus.OK);
            }
            return new ResponseEntity<>(
                    new ResponseResult<>("User not found", null), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
