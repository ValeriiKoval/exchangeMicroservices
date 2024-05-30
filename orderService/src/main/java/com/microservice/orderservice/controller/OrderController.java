package com.microservice.orderservice.controller;

import com.microservice.orderservice.facade.OrderFacade;
import com.microservice.orderservice.payload.request.OrderCreateRequest;
import com.microservice.orderservice.payload.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader final Long userId,
                                                     @RequestBody @Valid final OrderCreateRequest request) {
        return ResponseEntity.ok(orderFacade.createOrder(userId, request));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(@RequestHeader final Long userId) {
        return ResponseEntity.ok(orderFacade.getOrdersByUserId(userId));
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable final Long orderId) {
        return ResponseEntity.ok(orderFacade.cancelOrder(orderId));
    }
}
