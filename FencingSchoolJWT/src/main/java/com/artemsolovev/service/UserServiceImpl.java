package com.artemsolovev.service;

import com.artemsolovev.model.User;
import com.artemsolovev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User get(long id) {
        return this.userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User does not exists!"));
    }

    @Override
    public User get(String login) {
        return this.userRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("User does not exists"));
    }
}
