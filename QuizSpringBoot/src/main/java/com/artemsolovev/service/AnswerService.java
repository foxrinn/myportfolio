package com.artemsolovev.service;

import com.artemsolovev.model.Answer;

import java.util.List;

public interface AnswerService {
    void add(Answer answer, long idResult, long idUser);

    List<Answer> get(long idUser);

    Answer getByResult(long idResult);


}
