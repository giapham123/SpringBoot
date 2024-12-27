package com.example.demo.Facebook.services;

import com.example.demo.Facebook.commonFunc.ConfigCommonFuncFirefox;
import com.example.demo.common.GenericResponse;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class MultipleThreadExample {
    @Autowired
    AsyncTaskExecutor asyncTaskExecutor;

    @Autowired
    ConfigCommonFuncFirefox configCommonFuncFirefox;

    public GenericResponse multipleThread(String params,Integer time)throws InterruptedException {
        GenericResponse rs = new GenericResponse();
        String[] splitParams = params.split(",");
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        List<String> rsData = new ArrayList<>();
        for (int i = 0; i < splitParams.length; i++) {
            String param = splitParams[i]; // Assign splitParams[i] to a final local variable
            CompletableFuture<String> future;
            future = asyncTaskExecutor.processTask(splitParams[i], time);

            CompletableFuture<Void> resultFuture = future.thenAccept(result -> {
                System.out.println("Result for user " + param + ": " + result);
                rsData.add(result);
            }).exceptionally(ex -> {
                System.out.println("Error processing user " + param + ": " + ex.getMessage());
                return null;
            });
            futures.add(resultFuture);
        }
        rs.setData(rsData);
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        System.out.println("All tasks completed.");

        return rs;
    }
}
