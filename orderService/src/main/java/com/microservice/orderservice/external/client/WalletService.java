package com.microservice.orderservice.external.client;

import com.microservice.orderservice.config.FeignConfig;
import com.microservice.orderservice.payload.request.BalanceChangeRequest;
import com.microservice.orderservice.payload.response.WalletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "WALLET-SERVICE/wallet", configuration = FeignConfig.class)
public interface WalletService {

    @GetMapping
    WalletResponse getWallet();

    @PutMapping("/topup")
    void topup(BalanceChangeRequest request, @RequestHeader Map<String, String> headers);

    @GetMapping("/withdraw")
    void withdraw(BalanceChangeRequest request, @RequestHeader Map<String, String> headers);
}
