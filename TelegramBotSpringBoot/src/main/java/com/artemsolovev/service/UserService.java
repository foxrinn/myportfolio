package com.artemsolovev.service;

import com.artemsolovev.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserService {
    void add(User user);
    List<User> get();
    User get(long id);
    User get(String username);
    void addStep(String step, User user);
    void addLogin(String login, User user);
    void addName(String name, User user);
    void addAge(String age, User user);
}
