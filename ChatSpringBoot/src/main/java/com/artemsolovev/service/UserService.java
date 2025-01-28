package com.artemsolovev.service;

import com.artemsolovev.model.User;

import java.util.List;

public interface UserService {
    void add(User user);

    User get(long id);

    User get(String login, String password);

    List<User> get();
}
