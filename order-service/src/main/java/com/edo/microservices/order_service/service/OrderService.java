package com.edo.microservices.order_service.service;

import com.edo.microservices.order_service.dto.request.OrderRequest;

import com.edo.microservices.order_service.dto.request.OrderUpdateRequest;
import com.edo.microservices.order_service.dto.response.OrderResponse;
import com.edo.microservices.order_service.entity.Orders;
import com.edo.microservices.order_service.exception.AppException;
import com.edo.microservices.order_service.exception.ErrorCode;
import com.edo.microservices.order_service.mapper.OrderMapper;
import com.edo.microservices.order_service.repository.OrderRepository;

import kong.unirest.JsonNode;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;


import java.util.List;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)

public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Value("${spring.rabbitmq.server-host}")
    String serverHostSendRabbit;
    @Value("${spring.rabbitmq.queue}")
    String queueRabbit;
    @Autowired
    OrderMapper orderMapper;
    public OrderResponse createOrder(OrderRequest request){
        if(orderRepository.existsByOrderNumber(request.getOrderNumber()))
            throw new AppException(ErrorCode.ORDER_NUMBER_EXISTED);

        Orders order = orderRepository.save(orderMapper.toOrder(request));

        String messageRabbit = "Create OrderNumber: " + order.getOrderNumber();
        sendMessageRabbit(messageRabbit);
         return orderMapper.toOrderResponse(order);
    }

    public OrderResponse updateOrder(OrderUpdateRequest request){

        Orders order = orderRepository.findByOrderNumber(request.getOrderNumber())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));

        Orders orderUpdate = orderMapper.updateOrder(order, request);

        String messageRabbit = "Update OrderNumber: " + orderUpdate.getOrderNumber();

        orderRepository.save(orderUpdate);
        sendMessageRabbit(messageRabbit);
        return orderMapper.toOrderResponse(orderUpdate);
    }

    public OrderResponse getOrderByOrderNumber(Integer id){
        return orderMapper.toOrderResponse(orderRepository.findByOrderNumber(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED)));
    }

    public List<OrderResponse> getAllOrder(){
        return orderRepository.findAll().stream().map(order -> orderMapper.toOrderResponse(order)).toList();
    }

    public void deleteOrder(Integer id){
        Orders orders = orderRepository.findByOrderNumber(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));

        orderRepository.deleteById(orders.getId());
        String messageRabbit = "Delete OrderNumber: " + orders.getOrderNumber();
        sendMessageRabbit(messageRabbit);
    }


    public void sendMessageRabbit(String message) {
        try {
            String body = String.format("""
                    {
                        "queue":"%s",
                        "message":"%s"
                    }
                    """, queueRabbit, message);
            
            HttpResponse<JsonNode> response = Unirest.post(serverHostSendRabbit+"/notification-service/kafka")
                    .header("Content-Type", "application/json")
//                    .header("Authorization", "Bearer " + token)
                    .body(body)
                    .asJson();

        } catch (Exception e) {
            log.error("sendMessageRabbit : " + e.toString());

        }

    }
}
