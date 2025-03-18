package com.edo.microservices.notification_service.service;

import com.edo.microservices.notification_service.dto.request.ExchangeRequest;
import com.edo.microservices.notification_service.dto.request.QueueRequest;
import com.edo.microservices.notification_service.exception.AppException;
import com.edo.microservices.notification_service.exception.ErrorCode;
import lombok.AccessLevel;

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RabbitMQService {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendToQueue(QueueRequest request){
        try {
            rabbitTemplate.convertAndSend(request.getQueue(), request.getMessage());
            log.info("push-message: {} đến {}" , request.getMessage(), request.getQueue());
        } catch (Exception e) {
            log.error("sendToQueue : {}", e.toString() );
            throw new AppException(ErrorCode.ERROR_QUEUE);
        }

    }
    public void sendToExchange(ExchangeRequest request){
        try {
            rabbitTemplate.convertAndSend(request.getExchange(), request.getRouting(), request.getMessage());

            log.info("push-message: {} từ exchange {}" , request.getMessage(), request.getExchange());
        }catch (Exception e) {

            log.error("sendToExchange : {}", e.toString() );

            throw new AppException(ErrorCode.ERROR_EXCHANGE);
        }

    }
}
