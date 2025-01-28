package com.artemsolovev.service;

import com.artemsolovev.model.User;
import com.artemsolovev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void add(User user) {
        try {
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
            user.setActive(true);
            this.userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("User has already added!");
        }
    }

    @Override
    public User get(long idUser) {
        return this.userRepository.findById(idUser).orElseThrow(
                () -> new IllegalArgumentException("User does not exists!"));
    }

    @Override
    public User get(String login, String password) {
        return this.userRepository.findByLoginAndPassword(login, password)
                .orElseThrow(() -> new IllegalArgumentException("User does not exists"));
    }
}
