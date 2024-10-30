package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/basic")
public class BasicAuthController {

    @GetMapping("/create-order/{id}")
    public ResponseEntity<String> secureEndpointBasic(@PathVariable ("id") Integer id) throws Exception {

        if(id == 0){
            throw new Exception("This is a simulated runtime exception.");
        }
        return ResponseEntity.ok("Tạo Order Thành Công!");
    }
}
