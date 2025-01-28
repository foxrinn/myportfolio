package com.artemsolovev.service;

import com.artemsolovev.model.QuizUser;
import com.artemsolovev.repository.QuizUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizUserServiceImpl implements QuizUserService {
    private QuizUserRepository quizUserRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setQuizUserRepository(QuizUserRepository quizUserRepository) {
        this.quizUserRepository = quizUserRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void add(QuizUser quizUser) {
        try {
            quizUser.setPassword(this.passwordEncoder.encode(quizUser.getPassword()));
            this.quizUserRepository.save(quizUser);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Quiz user has already added!");
        }
    }

    @Override
    public QuizUser get(long id) {
        return this.quizUserRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Quiz user does not exists!"));
    }

    @Override
    public List<QuizUser> get() {
        return this.quizUserRepository.findAll();
    }
}
