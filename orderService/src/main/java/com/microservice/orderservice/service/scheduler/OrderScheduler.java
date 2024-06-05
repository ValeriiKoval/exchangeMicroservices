package com.microservice.orderservice.service.scheduler;

import com.microservice.orderservice.service.OrderService;
import com.microservice.orderservice.service.scheduler.processor.OrderPerformProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderScheduler {

    private final OrderService orderService;
    private final OrderPerformProcessor orderPerformProcessor;

    @Scheduled(initialDelay = 1000 * 60, fixedDelay = 1000 * 60)
    public void checkOrdersAndPerformIfPossible() {
        log.info("Performing orders scheduler started");
        final List<Long> orders = orderService.getOrderIdsInInProgressStatus();
        orders.forEach(orderPerformProcessor::performOrder);
        log.info("Performing orders scheduler finished");
    }
}
