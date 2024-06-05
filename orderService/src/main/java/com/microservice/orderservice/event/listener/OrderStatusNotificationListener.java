package com.microservice.orderservice.event.listener;

import com.microservice.orderservice.event.OrderStatusNotificationEvent;
import com.microservice.orderservice.external.client.UserService;
import com.microservice.orderservice.model.Message;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.model.TemplateEnum;
import com.microservice.orderservice.payload.response.UserResponse;
import com.microservice.orderservice.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderStatusNotificationListener {

    private final MailService mailService;
    private final UserService userService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @EventListener(OrderStatusNotificationEvent.class)
    @SneakyThrows
    public void onStatusChanged(OrderStatusNotificationEvent event) {
        final Order order = event.getOrder();
        final UserResponse userInfo = userService.getUserInfo(order.getUserId());
        final Map<String, Object> args = new HashMap<>();
        args.put("orderId", order.getId());
        args.put("status", order.getStatus().getName());
        final Message message = Message.builder()
                .subject("Order Status Update")
                .template(TemplateEnum.FAILED_ORDER)
                .args(args)
                .build();
        mailService.sendMail(userInfo.getEmail(), message);
    }
}
