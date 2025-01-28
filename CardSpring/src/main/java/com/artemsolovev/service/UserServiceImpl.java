package com.artemsolovev.service;

import com.artemsolovev.model.User;
import com.artemsolovev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
    public User get(long id) {
        return this.userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User does not exists!"));
    }

    @Override
    public User get(String login, String password) {
        return this.userRepository.findByLoginAndPassword(login, password)
                .orElseThrow(() -> new IllegalArgumentException("User is does not exists"));
    }

    @Override
    public User delete(long id) {
        User user = get(id);
        this.userRepository.deleteById(id);
        return user;
    }
}
