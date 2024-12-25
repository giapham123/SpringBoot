package com.example.demo.Facebook.controllers;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api")
public class ExampleController {

    @Operation(summary = "Get all items", description = "Fetches all the items from the database")
    @GetMapping("/greet")
    public String greet() {
        return "Hello, World!";
    }
}