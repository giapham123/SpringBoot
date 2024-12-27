package com.example.demo.Facebook.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncTaskExecutor {

    @Async("taskExecutor")
    public CompletableFuture<String> processTask(String userId, int processingTime) {
        try {
            System.out.println("Processing task for user: " + userId + " on thread: " + Thread.currentThread().getName());
            Thread.sleep(processingTime); // Simulate task processing
        } catch (InterruptedException e) {
            throw new RuntimeException("Task interrupted for user: " + userId, e);
        }
        return CompletableFuture.completedFuture("Task completed for user: " + userId);
    }
}
