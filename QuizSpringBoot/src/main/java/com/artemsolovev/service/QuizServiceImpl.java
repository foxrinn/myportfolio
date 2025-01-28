package com.artemsolovev.service;

import com.artemsolovev.model.Quiz;
import com.artemsolovev.model.QuizUser;
import com.artemsolovev.model.Result;
import com.artemsolovev.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("https://opentdb.com/api.php")
    private String url;

    private QuizUserService quizUserService;
    private ResultService resultService;
    private QuizRepository quizRepository;

    @Autowired
    public void setQuizRepository(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Autowired
    public void setQuizUserService(QuizUserService quizUserService) {
        this.quizUserService = quizUserService;
    }

    @Autowired
    public void setResultService(ResultService resultService) {
        this.resultService = resultService;
    }


    @Override
    public Quiz add(int amount, int category, String difficulty, long idUser) {
        QuizUser quizUser = this.quizUserService.get(idUser);
        Quiz quiz = this.restTemplate.exchange(this.url + "?amount={amount}&category={category}&difficulty={difficulty}", HttpMethod.GET, null,
                new ParameterizedTypeReference<Quiz>() {}, amount, category, difficulty).getBody();
        quiz.setQuizUser(quizUser);
        List<Result> results = quiz.getResults();
        for (Result result : results) {
            result.setQuiz(quiz);
        }
        this.quizRepository.save(quiz);
        return quiz;
    }

    @Override
    public List<Quiz> get(long idUser) {
        return this.quizRepository.findAllByQuizUser_Id(idUser);
    }
}
