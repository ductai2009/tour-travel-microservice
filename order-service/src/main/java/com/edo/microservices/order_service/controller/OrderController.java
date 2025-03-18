package com.edo.microservices.order_service.controller;

import com.edo.microservices.order_service.dto.request.OrderRequest;
import com.edo.microservices.order_service.dto.request.OrderUpdateRequest;
import com.edo.microservices.order_service.dto.response.OrderResponse;
import com.edo.microservices.order_service.dto.response.ResponseData;
import com.edo.microservices.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;


    @PostMapping
    ResponseData<OrderResponse> createOrder(@RequestBody @Valid OrderRequest request) {

        return ResponseData.<OrderResponse>builder()
                .code(1000)
                .result(orderService.createOrder(request))
                .message("Order created successfully")
                .build();
    }
    @PostMapping("/update")
    ResponseData<OrderResponse> updateTour(@Valid @RequestBody OrderUpdateRequest request) {
        OrderResponse response = orderService.updateOrder(request);

        return ResponseData.<OrderResponse>builder()
                .result(response)
                .message("Update order successfully")
                .build();
    }

    @GetMapping
    ResponseData<List<OrderResponse>> getAllOrder(){
        return ResponseData.<List<OrderResponse>>builder()
                .result(orderService.getAllOrder())
                .build();
    }
    @GetMapping("/{id}")
    ResponseData<OrderResponse> getOrderByOrderNumber(@PathVariable("id") Integer id) {
        OrderResponse response = orderService.getOrderByOrderNumber(id);

        return ResponseData.<OrderResponse>builder()
                .result(response)
                .message("Get order successfully")
                .build();
    }
    @DeleteMapping("/{id}")
    ResponseData<String> deleteById(@PathVariable("id") Integer id) {
        orderService.deleteOrder(id);
        return ResponseData.<String>builder()
                .message("Delete order successfully")
                .build();
    }
}
