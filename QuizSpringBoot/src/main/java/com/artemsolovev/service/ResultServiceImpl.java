package com.artemsolovev.service;

import com.artemsolovev.model.Result;
import com.artemsolovev.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultServiceImpl implements ResultService {

    private ResultRepository resultRepository;

    @Autowired
    public void setResultRepository(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }


    @Override
    public void add(Result result) {
        this.resultRepository.save(result);
    }

    @Override
    public Result get(long id) {
        return this.resultRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Result does not exists"));
    }
}
