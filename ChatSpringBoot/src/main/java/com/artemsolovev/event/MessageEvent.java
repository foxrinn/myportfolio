package com.artemsolovev.event;

import com.artemsolovev.model.Message;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class MessageEvent extends ApplicationEvent {

    private Message message;

    public MessageEvent(Object source, Message message) {
        super(source);
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
