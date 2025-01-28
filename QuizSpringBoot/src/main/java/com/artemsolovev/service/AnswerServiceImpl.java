package com.artemsolovev.service;

import com.artemsolovev.model.Answer;
import com.artemsolovev.model.Result;
import com.artemsolovev.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    private AnswerRepository answerRepository;
    private ResultService resultService;
    private QuizUserService quizUserService;

    @Autowired
    public void setAnswerRepository(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Autowired
    public void setResultService(ResultService resultService) {
        this.resultService = resultService;
    }

    @Autowired
    public void setQuizUserService(QuizUserService quizUserService) {
        this.quizUserService = quizUserService;
    }

    @Override
    public void add(Answer answer, long idResult, long idUser) {
        Result result = resultService.get(idResult);
        answer.setCorrect(false);
        if (result.getCorrectAnswer().equals(answer.getAnswer())) {
            answer.setCorrect(true);
        }
        answer.setResult(resultService.get(idResult));
        answer.setQuizUser(quizUserService.get(idUser));
        this.answerRepository.save(answer);
    }

    @Override
    public List<Answer> get(long idUser) {
        return this.answerRepository.findAllByQuizUser_Id(idUser);
    }

    @Override
    public Answer getByResult(long idResult) {
        return this.answerRepository.findTopByResult_Id(idResult).orElseThrow(
                () -> new IllegalArgumentException("Result does not exists!"));
    }
}
