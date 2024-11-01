package com.example.demo.controllers;

import com.example.demo.CircuitBreaker.SimpleCircuitBreaker;
import com.example.demo.CircuitBreaker.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/basic")
public class BasicAuthController {

    private final RestTemplate restTemplate;
    private final SimpleCircuitBreaker<String> circuitBreaker;

    @Autowired
    public BasicAuthController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.circuitBreaker = new SimpleCircuitBreaker<>(5, 10000); // ngưỡng 5 lỗi, timeout 10 giây
    }
    @GetMapping("/create-order/{id}")
    public ResponseEntity<String> secureEndpointBasic(@PathVariable ("id") Integer id) throws Exception {
        if(id == 0){
            throw new Exception("This is a simulated runtime exception.");
        }
        return ResponseEntity.ok("Tạo Order Thành Công!");
    }

    @GetMapping("/circuitBreaker")
    public ResponseEntity<String> checkCircuitBreaker() throws Exception {
        // Định nghĩa hàm fallback
        Supplier<String> fallback = () -> "Fallback User: "; // Chuỗi mặc định


        String a=  circuitBreaker.call(() -> {
            String user = restTemplate.getForObject("http://localhost:8089/api/checkLogin/create-order/1", String.class);
            return user != null ? user : ""; // Trả về chuỗi rỗng nếu không tìm thấy
        },"ádadadsasdasdasdsd"); // Gọi hàm fallback nếu có lỗi
        return ResponseEntity.ok(a);
    }
}
