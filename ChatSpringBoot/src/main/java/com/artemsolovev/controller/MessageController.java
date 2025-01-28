package com.artemsolovev.controller;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.event.MessageEvent;
import com.artemsolovev.model.Message;
import com.artemsolovev.model.User;
import com.artemsolovev.repository.SseEmitters;
import com.artemsolovev.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/sse")
public class MessageController implements ApplicationListener<MessageEvent> {

    private MessageService messageService;
    private SseEmitters emitters;

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @Autowired
    public void setSseEmitters(SseEmitters emitters) {
        this.emitters = emitters;
    }


    @PostMapping(path = "/message/{id}")
    public ResponseEntity<ResponseResult<Message>> add(@RequestBody Message message, @PathVariable long id) {
        try {
            this.messageService.add(message, id);
            return new ResponseEntity<>(new ResponseResult<>(null, message), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/chat/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter get(@PathVariable long id) {
        return emitters.add(new SseEmitter(), id);
    }

    @Override
    public void onApplicationEvent(MessageEvent messageEvent) {
        emitters.send(messageEvent.getMessage());
    }

    @GetMapping
    public ResponseEntity<ResponseResult<List<Message>>> get() {
        List<Message> list = this.messageService.get();
        return new ResponseEntity<>(new ResponseResult<>(null, list), HttpStatus.OK);
    }
}
