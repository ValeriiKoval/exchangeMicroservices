package com.microservice.orderservice.service.scheduler.processor;

import com.microservice.orderservice.external.client.ExchangeService;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.model.TypeEnum;
import com.microservice.orderservice.payload.response.CompanyInfoResponse;
import com.microservice.orderservice.service.OrderService;
import com.microservice.orderservice.service.ShareService;
import com.microservice.orderservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderPerformProcessor {

    private final OrderService orderService;
    private final WalletService walletService;
    private final ShareService shareService;
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
        final Double companyPrice = companyInfo.getPrice();
        if (order.getPrice() < companyPrice) {
            return;
        }
        final double blockedMoney = order.getPrice() * order.getAmount();
        final double spentMoney = companyPrice * order.getAmount();
        final double difference = blockedMoney - spentMoney;
        walletService.topup(difference, order);
        orderService.successOrder(order);
        shareService.addOrCreate(order.getUserId(), order.getCompanySymbol(), order.getAmount());
        log.info("Performing order {} successes", order.getId());

    }

    private void performSellOrder(final Order order, final CompanyInfoResponse companyInfo) {
        if (order.getPrice() > companyInfo.getPrice()) {
            return;
        }
        walletService.topup(companyInfo.getPrice() * order.getAmount(), order);
        orderService.successOrder(order);
        log.info("Performing order {} successes", order.getId());
    }
}
