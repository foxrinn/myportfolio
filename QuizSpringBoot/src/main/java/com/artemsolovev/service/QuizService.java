package com.artemsolovev.service;

import com.artemsolovev.model.Quiz;

import java.util.List;

public interface QuizService {
    Quiz add(int amount, int category, String difficulty, long idUser);

    List<Quiz> get(long idUser);
}
