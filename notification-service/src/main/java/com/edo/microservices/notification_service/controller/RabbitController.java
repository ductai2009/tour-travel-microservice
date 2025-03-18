package com.edo.microservices.notification_service.controller;


import com.edo.microservices.notification_service.dto.request.QueueRequest;
import com.edo.microservices.notification_service.dto.response.ResponseData;

import com.edo.microservices.notification_service.service.RabbitMQService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/rabbitmq")
public class RabbitController {

    @Autowired
    RabbitMQService rabbitMQService;

    @PostMapping("/push-message")
    ResponseData<String> sendMessage(@RequestBody QueueRequest request) {
        rabbitMQService.sendToQueue(request);
        return ResponseData.<String>builder()
                .message("Push message successfully")
                .build();
    }
}
