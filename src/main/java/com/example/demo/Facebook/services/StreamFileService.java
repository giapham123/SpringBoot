package com.example.demo.Facebook.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class StreamFileService {

    @Qualifier("taskExecutor") // Sử dụng taskExecutor được cấu hình
    private final Executor taskExecutor;

    public StreamFileService(@Qualifier("taskExecutor") Executor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    // Giả lập xử lý thanh toán
    private String processUser(String payment) {
        try {
            long duration = (long) (Math.random() * 5000 + 1000); // Giả lập thời gian xử lý
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Processed payment for: " + payment + " by " + Thread.currentThread().getName();
    }

    // Xử lý thanh toán không đồng bộ
    public void processStream(List<String> users, SseEmitter emitter) {
        List<CompletableFuture<Void>> futures = users.stream()
                .map(user -> CompletableFuture.runAsync(() -> {
                    try {
                        String result = processUser(user);
                        emitter.send(result); // Gửi kết quả ngay khi hoàn thành
                    } catch (Exception e) {
                        try {
                            emitter.send("Error processing user for: " + user);
                        } catch (Exception ignored) {}
                    }
                }, taskExecutor)) // Sử dụng executor tùy chỉnh
                .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join(); // Đợi tất cả hoàn thành
        emitter.complete(); // Đóng SSE
    }
}
