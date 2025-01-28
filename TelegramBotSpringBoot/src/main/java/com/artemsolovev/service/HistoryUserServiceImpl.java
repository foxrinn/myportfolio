package com.artemsolovev.service;

import com.artemsolovev.model.ComplimentUser;
import com.artemsolovev.model.HistoryUser;
import com.artemsolovev.repository.HistoryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class HistoryUserServiceImpl implements HistoryUserService {

    private HistoryUserRepository historyUserRepository;

    @Autowired
    public void setHistoryUserRepository(HistoryUserRepository historyUserRepository) {
        this.historyUserRepository = historyUserRepository;
    }

    @Override
    public void add(HistoryUser historyUser) {
        try {
            this.historyUserRepository.save(historyUser);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("History has already added!");
        }
    }
}
