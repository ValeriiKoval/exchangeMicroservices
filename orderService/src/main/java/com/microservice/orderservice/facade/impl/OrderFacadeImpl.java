package com.microservice.orderservice.facade.impl;

import com.microservice.orderservice.exception.InvalidOperationException;
import com.microservice.orderservice.exception.ResourceNotFoundException;
import com.microservice.orderservice.external.client.ExchangeService;
import com.microservice.orderservice.facade.OrderFacade;
import com.microservice.orderservice.mapper.OrderMapper;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.model.StatusEnum;
import com.microservice.orderservice.payload.request.OrderCreateRequest;
import com.microservice.orderservice.payload.response.CompanyInfoResponse;
import com.microservice.orderservice.payload.response.OrderResponse;
import com.microservice.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderFacadeImpl implements OrderFacade {

    private final OrderService orderService;
    private final ExchangeService exchangeService;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderResponse> getOrdersByUserId(final Long userId) {
        final List<Order> orders = orderService.findAllByUserId(userId);
        return orderMapper.toOrderResponseList(orders);
    }

    @Override
    public OrderResponse createOrder(final Long userId, final OrderCreateRequest request) {
        final CompanyInfoResponse companyInfo = exchangeService.getCompanyInfo(request.getCompanySymbol());
        if (!companyInfo.getSymbol().equals(request.getCompanySymbol())) {
            throw new ResourceNotFoundException("Company by symbol " + request.getCompanySymbol() + " not found.");
        }
        final Order order = orderService.createOrder(userId, request);
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public OrderResponse cancelOrder(final Long orderId) {
        final Order order = orderService.findById(orderId);
        if (!StatusEnum.IN_PROGRESS.equals(order.getStatus())) {
            throw new InvalidOperationException("Order in final status can not be updated");
        }
        return orderMapper.toOrderResponse(orderService.cancelOrder(order));
    }
}
