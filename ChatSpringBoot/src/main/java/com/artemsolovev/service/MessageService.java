package com.artemsolovev.service;

import com.artemsolovev.model.Message;

import java.util.List;

public interface MessageService {

    void add(Message message, long idUser);

    List<Message> get();
}
