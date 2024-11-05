package com.example.demo.controllers;

import com.example.demo.config.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    public MessageController(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @GetMapping("/send")
    public String sendMessage(@RequestParam String message) {
        rabbitMQProducer.sendMessage(message);
        return "Message sent to RabbitMQ: " + message;
    }
}
