package com.example.demo.Facebook.controllers;

import com.example.demo.Facebook.services.MultipleThreadExample;
import com.example.demo.Facebook.services.StreamFileService;
import com.example.demo.common.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class ExampleController {

    @Autowired
    MultipleThreadExample multipleThreadExample;

    private final StreamFileService streamFileService;

    public ExampleController(StreamFileService streamFileService) {
        this.streamFileService = streamFileService;
    }

    @Operation(summary = "Get all items", description = "Fetches all the items from the database")
    @GetMapping("/greet")
    public GenericResponse greet(@RequestParam ("data") String data, @RequestParam ("time") Integer time) throws InterruptedException {
        return multipleThreadExample.multipleThread(data,time);
    }

    // API xử lý thanh toán
    @PostMapping("/stream-multiple-thread")
    public SseEmitter processStream(@RequestBody List<String> users) {
        SseEmitter emitter = new SseEmitter(); // Tạo SSE emitter để stream kết quả

        CompletableFuture.runAsync(() -> {
            try {
                streamFileService.processStream(users, emitter);
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

}