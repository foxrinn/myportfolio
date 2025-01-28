package com.artemsolovev.service;

import com.artemsolovev.model.User;

public interface UserService {
    User get(long id);
    User get(String login);
}
