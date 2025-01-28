package com.artemsolovev.service;

import com.artemsolovev.model.Result;

public interface ResultService {
    void add(Result result);

    Result get(long id);
}
