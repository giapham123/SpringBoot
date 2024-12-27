package com.example.demo.Facebook.controllers;

import com.example.demo.Facebook.services.MultipleThreadExample;
import com.example.demo.common.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class ExampleController {

    @Autowired
    MultipleThreadExample multipleThreadExample;

    @Operation(summary = "Get all items", description = "Fetches all the items from the database")
    @GetMapping("/greet")
    public GenericResponse greet(@RequestParam ("data") String data, @RequestParam ("time") Integer time) throws InterruptedException {
        return multipleThreadExample.multipleThread(data,time);
    }

}