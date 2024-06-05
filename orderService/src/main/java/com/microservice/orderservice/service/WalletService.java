package com.microservice.orderservice.service;

import com.microservice.orderservice.model.Order;

public interface WalletService {
    void topup(double amount, Order order);

    void withdraw(double amount, Order order);
}
