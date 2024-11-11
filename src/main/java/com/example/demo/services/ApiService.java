package com.example.demo.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class ApiService {

    @Async("taskExecutor")
    public CompletableFuture<String> processRequest(String userId, Integer time) throws InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            // Giả lập xử lý nhiệm vụ có thể gây ra ngoại lệ
            if (time < 0) {
                throw new IllegalArgumentException("Thời gian không hợp lệ!");
            }
            // Giả lập xử lý nhiệm vụ
            long startTime = System.currentTimeMillis(); // Thời gian bắt đầu xử lý
            System.out.println("Bắt đầu xử lý cho user " + userId + " tại " + startTime + " bằng luồng " + Thread.currentThread().getName());

            try {
                Thread.sleep(time); // Giả lập xử lý tác vụ
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            long endTime = System.currentTimeMillis(); // Thời gian kết thúc xử lý
            System.out.println("Kết thúc xử lý cho user " + userId + " tại " + endTime + " bằng luồng " + Thread.currentThread().getName());

            String result = "Kết quả cho user " + userId;
            return result;
        }).exceptionally(ex -> {
            System.out.println("Ngoại lệ trong tác vụ async: " + ex.getMessage());
            return "Có lỗi xảy ra!";
        });
    }

    public String processRequest1(String userId, Integer time)  {
        long startTime = System.currentTimeMillis(); // Thời gian bắt đầu xử lý
        System.out.println("Bắt đầu xử lý cho user " + userId + " tại " + startTime);

        try {
            Thread.sleep(time); // Giả lập xử lý tác vụ
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long endTime = System.currentTimeMillis(); // Thời gian kết thúc xử lý
        System.out.println("Kết thúc xử lý cho user " + userId + " tại " + endTime);

        String result = "Kết quả cho user " + userId;
        return result;
    }
}