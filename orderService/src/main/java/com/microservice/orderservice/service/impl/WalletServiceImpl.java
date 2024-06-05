package com.microservice.orderservice.service.impl;

import com.microservice.orderservice.external.client.WalletClient;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.payload.request.BalanceChangeRequest;
import com.microservice.orderservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletClient walletClient;

    @Override
    public void topup(final double amount, final Order order) {
        final BalanceChangeRequest balanceChangeRequest = new BalanceChangeRequest();
        balanceChangeRequest.setAmount(amount);
        walletClient.topup(balanceChangeRequest, this.getHeaders(order));
    }

    @Override
    public void withdraw(final double amount, final Order order) {
        final BalanceChangeRequest balanceChangeRequest = new BalanceChangeRequest();
        balanceChangeRequest.setAmount(amount);
        walletClient.withdraw(balanceChangeRequest, this.getHeaders(order));
    }

    private Map<String, String> getHeaders(final Order order) {
        return Map.of("userId", order.getUserId().toString());
    }
}
