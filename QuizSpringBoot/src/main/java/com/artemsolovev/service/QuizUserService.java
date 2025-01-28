package com.artemsolovev.service;

import com.artemsolovev.model.QuizUser;

import java.util.List;

public interface QuizUserService {
    void add(QuizUser quizUser);

    QuizUser get(long id);
    List<QuizUser> get();
}
