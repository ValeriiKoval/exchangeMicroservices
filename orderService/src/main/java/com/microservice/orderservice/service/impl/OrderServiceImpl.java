package com.microservice.orderservice.service.impl;

import com.microservice.orderservice.event.OrderStatusNotificationEvent;
import com.microservice.orderservice.exception.ResourceNotFoundException;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.model.Share;
import com.microservice.orderservice.model.StatusEnum;
import com.microservice.orderservice.model.TypeEnum;
import com.microservice.orderservice.payload.request.OrderCreateRequest;
import com.microservice.orderservice.repository.OrderRepository;
import com.microservice.orderservice.service.OrderService;
import com.microservice.orderservice.service.ShareService;
import com.microservice.orderservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WalletService walletService;
    private final ShareService shareService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Order findById(final Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + orderId + " not found"));
    }

    @Override
    public List<Order> findAllByUserId(final Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public Order saveOrder(final Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order createOrder(final Long userId, final OrderCreateRequest request) {
        final Order order = new Order();
        order.setUserId(userId);
        order.setStatus(StatusEnum.IN_PROGRESS);
        order.setCompanySymbol(request.getCompanySymbol());
        order.setPrice(request.getPrice());
        order.setAmount(request.getAmount());
        order.setType(TypeEnum.valueOf(request.getType()));
        this.blockResourcesForOrder(order);
        return this.saveOrder(order);
    }

    @Override
    public Order cancelOrder(final Order order) {
        this.unblockResourcesForOrder(order);
        order.setStatus(StatusEnum.CANCELED);
        return this.saveOrder(order);
    }

    @Override
    public Order failOrder(final Order order) {
        this.unblockResourcesForOrder(order);
        order.setStatus(StatusEnum.FAILED);
        eventPublisher.publishEvent(OrderStatusNotificationEvent.builder()
                .order(order)
                .build());
        return this.saveOrder(order);
    }

    @Override
    public Order successOrder(final Order order) {
        order.setStatus(StatusEnum.SUCCESS);
        eventPublisher.publishEvent(OrderStatusNotificationEvent.builder()
                .order(order)
                .build());
        return this.saveOrder(order);
    }

    @Override
    public List<Long> getOrderIdsInInProgressStatus() {
        return orderRepository.findAllByStatusIsInProgress();
    }

    private void blockResourcesForOrder(final Order order) {
        if (TypeEnum.BUY.equals(order.getType())) {
            final double price = order.getPrice() * order.getAmount();
            walletService.withdraw(price, order);
        }
        if (TypeEnum.SELL.equals(order.getType())) {
            final Share share = shareService.findByUserIdAndCompanySymbol(order.getUserId(), order.getCompanySymbol());
            shareService.reduceAmount(share, order.getAmount());
        }
    }

    private void unblockResourcesForOrder(final Order order) {
        if (TypeEnum.BUY.equals(order.getType())) {
            final double price = order.getPrice() * order.getAmount();
            walletService.topup(price, order);
        }
        if (TypeEnum.SELL.equals(order.getType())) {
            final Share share = shareService.findByUserIdAndCompanySymbol(order.getUserId(), order.getCompanySymbol());
            shareService.increaseAmount(share, order.getAmount());
        }
    }

}
