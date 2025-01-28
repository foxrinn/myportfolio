package com.artemsolovev.service;

import com.artemsolovev.model.User;

public interface UserService {
    void add(User user);
    User get(long idUser);
    User get(String login, String password);
}
