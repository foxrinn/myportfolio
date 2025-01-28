package com.artemsolovev.service;

import com.artemsolovev.model.User;
import com.artemsolovev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void add(User user) {
        try {
            this.userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("User has already added!");
        }
    }

    @Override
    public List<User> get() {
        return this.userRepository.findAll();
    }

    @Override
    public User get(long id) {
        return this.userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User does not exists!"));
    }

    @Override
    public User get(String username) {
        return this.userRepository.findByTelegramUserName(username);
    }

    @Override
    public void addStep(String step, User user) {
        user.setStep(step);
        this.userRepository.save(user);
    }

    @Override
    public void addLogin(String login, User user) {
        user.setLogin(login);
        this.userRepository.save(user);
    }

    @Override
    public void addName(String name, User user) {
        user.setName(name);
        this.userRepository.save(user);
    }

    @Override
    public void addAge(String age, User user) {
        user.setAge(age);
        this.userRepository.save(user);
    }
}
