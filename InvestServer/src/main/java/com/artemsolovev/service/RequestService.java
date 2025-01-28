package com.artemsolovev.service;

import com.artemsolovev.model.Request;

import java.util.List;

public interface RequestService {
    void add(long idUser, Request request);
    List<Request> get();
    List<Request> get(long idUser);
    List<Request> findAllByUserId(long idUser);

    Request getById(long id);

    Request makeDecision(long id, boolean decision, String comment);
}
