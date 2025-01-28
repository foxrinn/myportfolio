package com.artemsolovev.service;

import com.artemsolovev.model.Apprentice;

import java.util.List;

public interface ApprenticeService {
    void add(Apprentice apprentice);
    Apprentice get(long id);
    List<Apprentice> get();
    Apprentice delete(long id);
    Apprentice update(Apprentice apprentice);
}
