package com.artemsolovev.service;

import com.artemsolovev.event.MessageEvent;
import com.artemsolovev.model.Message;
import com.artemsolovev.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;

    private UserService userService;

    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public MessageServiceImpl(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void add(Message message, long idUser) {
        try {
            message.setUser(userService.get(idUser));
            this.messageRepository.save(message);
            applicationEventPublisher.publishEvent(new MessageEvent(this, message));
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Message has already added!");
        }
    }

    @Override
    public List<Message> get() {
        return this.messageRepository.findAll();
    }


}
