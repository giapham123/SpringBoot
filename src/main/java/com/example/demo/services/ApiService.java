package com.example.demo.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class ApiService {

    @Async("taskExecutor")
    public CompletableFuture<String> processRequest(String userId, Integer time) throws InterruptedException {
        // Giả lập xử lý nhiệm vụ
        Thread.sleep(time); // Xử lý mất 2 giây cho mỗi yêu cầu
        String result = "Kết quả cho user " + userId;
        System.out.println("Xử lý xong cho " + userId);
        return CompletableFuture.completedFuture(result);
    }
}