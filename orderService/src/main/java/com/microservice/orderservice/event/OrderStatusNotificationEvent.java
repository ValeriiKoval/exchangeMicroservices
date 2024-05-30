package com.microservice.orderservice.event;

import com.microservice.orderservice.model.Order;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderStatusNotificationEvent {
    private Order order;
}
