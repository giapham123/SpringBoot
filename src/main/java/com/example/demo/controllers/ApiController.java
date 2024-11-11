package com.example.demo.controllers;

import com.example.demo.services.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/basic")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @GetMapping("/aaa")
    public CompletableFuture<String> handleRequest(@RequestParam String userId,@RequestParam Integer time) throws InterruptedException {
        return apiService.processRequest(userId,time);
    }

    @GetMapping("/aaaa")
    public String handleRequest1(@RequestParam String userId,@RequestParam Integer time) {
        return apiService.processRequest1(userId,time);
    }
}