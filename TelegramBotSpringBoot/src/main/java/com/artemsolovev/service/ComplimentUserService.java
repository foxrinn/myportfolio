package com.artemsolovev.service;

import com.artemsolovev.model.Compliment;
import com.artemsolovev.model.ComplimentUser;

import java.util.List;

public interface ComplimentUserService {

    void add(ComplimentUser complimentUser);

    List<ComplimentUser> get(long idUser);

    void delete(long idUser);
}
