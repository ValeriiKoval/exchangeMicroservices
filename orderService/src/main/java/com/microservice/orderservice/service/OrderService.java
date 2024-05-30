package com.microservice.orderservice.service;

import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.payload.request.OrderCreateRequest;

import java.util.List;

public interface OrderService {
    Order findById(Long orderId);

    List<Order> findAllByUserId(Long userId);

    Order saveOrder(Order order);

    Order createOrder(Long userId, OrderCreateRequest request);

    Order cancelOrder(Order order);

    Order failOrder(Order order);

    Order successOrder(Order order);

    List<Long> getOrderIdsInInProgressStatus();
}
