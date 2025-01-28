package com.artemsolovev.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Repository
public class SseEmitters {
    private static final Logger logger = LoggerFactory.getLogger(SseEmitters.class);
    private ConcurrentHashMap<Long, List<SseEmitter>> hashMap = new ConcurrentHashMap<>();
    private ExecutorService singleThreadExecutor;

    public SseEmitter add(SseEmitter emitter, Long id) {

        List<SseEmitter> emitters = this.hashMap.getOrDefault(id, new CopyOnWriteArrayList<>());
        emitters.add(emitter);
        hashMap.put(id, emitters);

        emitter.onCompletion(() -> {
            logger.info("Emitter completed: {}", emitter);
            emitters.remove(emitter);
            hashMap.put(id, emitters);
        });

        emitter.onTimeout(() -> {
            logger.info("Emitter timed out: {}", emitter);
            emitter.complete();
            emitters.remove(emitter);
            hashMap.put(id, emitters);
        });

        return emitter;
    }


    public void send(Object obj) {
        Collection<List<SseEmitter>> values = this.hashMap.values();
        for (List<SseEmitter> value : values) {
            for (SseEmitter emitter : value) {
                this.singleThreadExecutor = Executors.newSingleThreadExecutor();
                singleThreadExecutor.execute(() -> {
                    try {
                        emitter.send(obj);
                    } catch (IOException e) {
                        emitter.completeWithError(e);
                        logger.error("Emitter failed: {}", emitter, e);
                    }
                });
            }
        }
    }

    public ConcurrentHashMap<Long, List<SseEmitter>> getHashMap() {
        return hashMap;
    }
}
