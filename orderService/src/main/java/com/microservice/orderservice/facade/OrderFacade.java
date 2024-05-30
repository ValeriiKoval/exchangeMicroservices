package com.microservice.orderservice.facade;

import com.microservice.orderservice.payload.request.OrderCreateRequest;
import com.microservice.orderservice.payload.response.OrderResponse;

import java.util.List;

public interface OrderFacade {
    List<OrderResponse> getOrdersByUserId(Long userId);

    OrderResponse createOrder(Long userId, OrderCreateRequest request);

    OrderResponse cancelOrder(Long orderId);
}
