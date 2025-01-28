package com.artemsolovev.service;

import com.artemsolovev.model.Compliment;

import java.util.List;

public interface ComplimentService {
    List<Compliment> get();
    Compliment get(int id);
    Compliment getRandom(long idUser);
}
