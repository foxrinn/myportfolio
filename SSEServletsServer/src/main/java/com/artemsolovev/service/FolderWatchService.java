package com.artemsolovev.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.artemsolovev.dto.Event;
import com.artemsolovev.repository.SSEEmittersRepository;

import javax.servlet.AsyncContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class FolderWatchService {
    private SSEEmittersRepository repository;

    private BlockingQueue<Event> messageBlockingQueue = new LinkedBlockingQueue<>();

    private ExecutorService singleThreadExecutorWatcher;
    private ExecutorService singleThreadExecutorTasker;

    private void sendMessage(PrintWriter writer, Event message) {
        try {
            writer.println("data: " + new ObjectMapper().writeValueAsString(message));
            writer.println();
            writer.flush();
        } catch (Exception ignored) {
        }
    }

    public FolderWatchService(String folderName, SSEEmittersRepository repository) {
        this.repository = repository;
        this.startMessageReceive();
        this.startFolderWatch(folderName);
    }

    private void startMessageReceive() {
        singleThreadExecutorTasker = Executors.newSingleThreadExecutor();
        singleThreadExecutorTasker.execute(() -> {
            try {
                while (true) {
                    Event message = messageBlockingQueue.take();
                    System.out.println("Start sending\n" + repository.getList());
                    for (AsyncContext asyncContext : repository.getList()) {
                        try {
                            sendMessage(asyncContext.getResponse().getWriter(), message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Thread is interrupting");
            }
            System.out.println("Thread is interrupted");
        });
    }

    private void startFolderWatch(String folderName) {
        singleThreadExecutorWatcher = Executors.newSingleThreadExecutor();
        singleThreadExecutorWatcher.execute(() -> {
            try {
                WatchService watchService = FileSystems.getDefault().newWatchService();
                Path folder = Paths.get(folderName);
                folder.register(watchService,
                        StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_MODIFY);

                WatchKey key;
                while ((key = watchService.take()) != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();

                        if (kind == StandardWatchEventKinds.OVERFLOW) {
                            continue;
                        }

                        WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
                        Path path = folder.resolve(pathEvent.context());
                        messageBlockingQueue.add(new Event(pathEvent.kind().toString(), path));
                    }

                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }
                watchService.close();
            } catch (InterruptedException e) {
                //e.printStackTrace();
                //Thread.currentThread().interrupt();
                System.out.println("Thread is interrupting");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Thread is interrupted");
        });
    }

    public void stop() {
        this.singleThreadExecutorTasker.shutdownNow();
        this.singleThreadExecutorWatcher.shutdownNow();
        this.repository.clear();
        this.messageBlockingQueue.clear();
    }
}
