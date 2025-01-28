package com.artemsolovev.service;

import com.artemsolovev.model.User;

import java.util.List;

public interface UserService {
    void add(User user);
    List<User> get();
    User get(long id);
    User getByLoginAndPassword(String login, String password);
}
