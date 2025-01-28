package com.artemsolovev.service;

import com.artemsolovev.model.Trainer;

import java.util.List;

public interface TrainerService {
    void add(Trainer trainer);
    Trainer get(long id);
    List<Trainer> get();
    Trainer delete(long id);
    Trainer update(Trainer trainer);
}
