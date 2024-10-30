package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/basic")
public class BasicAuthController {

    @GetMapping("/create-products/{id}")
    public ResponseEntity<String> secureEndpointBasic(@PathVariable ("id") Integer id) {
        if(id > 1){
            return ResponseEntity.ok("Tạo Product Không Thành Công, Vì Id Lớn Hơn 1!");
        }
        return ResponseEntity.ok("Tạo Product Thành Công!");
    }
}
