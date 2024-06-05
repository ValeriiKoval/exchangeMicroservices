package com.microservice.orderservice.service.scheduler.processor;

import com.microservice.orderservice.external.client.ExchangeService;
import com.microservice.orderservice.external.client.WalletService;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.model.TypeEnum;
import com.microservice.orderservice.payload.request.BalanceChangeRequest;
import com.microservice.orderservice.payload.response.CompanyInfoResponse;
import com.microservice.orderservice.payload.response.WalletResponse;
import com.microservice.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderPerformProcessor {

    private final OrderService orderService;
    private final WalletService walletService;
    private final ExchangeService exchangeService;

    @Transactional
    public void performOrder(final Long orderId) {
        log.info("Performing order {} started", orderId);
        final Order order = orderService.findById(orderId);
        final CompanyInfoResponse companyInfo = exchangeService.getCompanyInfo(order.getCompanySymbol());
        if (!companyInfo.getSymbol().equals(order.getCompanySymbol())) {
            orderService.failOrder(order);
            log.info("Performing order {} failed", orderId);
            return;
        }

        if (order.getType() == TypeEnum.BUY) {
            performBuyOrder(order, companyInfo);
        } else if (order.getType() == TypeEnum.SELL) {
            performSellOrder(order, companyInfo);
        }
        log.info("Performing order {} finished", orderId);
    }

    private void performBuyOrder(final Order order, final CompanyInfoResponse companyInfo) {
        if (order.getPrice() < companyInfo.getPrice()) {
            return;
        }
        final double price = order.getPrice() * order.getAmount();
        final WalletResponse wallet = walletService.getWallet();
        if (wallet.getBalance() < price) {
            orderService.failOrder(order);
            log.info("Performing order {} failed; Small balance", order.getId());
        } else {
            final BalanceChangeRequest balanceChangeRequest = new BalanceChangeRequest();
            balanceChangeRequest.setAmount(price);
            walletService.withdraw(balanceChangeRequest, this.getHeaders(order));
            orderService.successOrder(order);
            log.info("Performing order {} successes", order.getId());
        }
    }

    private void performSellOrder(final Order order, final CompanyInfoResponse companyInfo) {
        if (order.getPrice() > companyInfo.getPrice()) {
            return;
        }
        final BalanceChangeRequest balanceChangeRequest = new BalanceChangeRequest();
        balanceChangeRequest.setAmount(order.getPrice() * order.getAmount());
        walletService.topup(balanceChangeRequest, this.getHeaders(order));
        orderService.successOrder(order);
        log.info("Performing order {} successes", order.getId());
    }

    private Map<String, String> getHeaders(final Order order) {
        return Map.of("userId", order.getUserId().toString());
    }
}
