package com.artemsolovev.service;

import com.artemsolovev.model.User;
import com.artemsolovev.repository.SseEmitters;
import com.artemsolovev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.AsyncContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private SseEmitters emitters;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setSseEmitters(SseEmitters emitters) {
        this.emitters = emitters;
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
                .orElseThrow(() -> new IllegalArgumentException("User does not exists"));
    }

    @Override
    public List<User> get() {
        List<User> onlineUsers = new ArrayList<>();
        Set<Map.Entry<Long, List<SseEmitter>>> entries = emitters.getHashMap().entrySet();
        for (Map.Entry<Long, List<SseEmitter>> entry : entries) {
            if (entry.getValue().size() != 0)
                onlineUsers.add(this.get(entry.getKey()));
        }
        return onlineUsers;
    }
}
