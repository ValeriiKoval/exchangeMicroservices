package com.microservice.orderservice.external.client;

import com.microservice.orderservice.config.FeignConfig;
import com.microservice.orderservice.payload.request.BalanceChangeRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "WALLET-SERVICE/wallet", configuration = FeignConfig.class)
public interface WalletClient {

    @PutMapping("/topup")
    void topup(@RequestBody BalanceChangeRequest request, @RequestHeader Map<String, String> headers);

    @PutMapping("/withdraw")
    void withdraw(@RequestBody BalanceChangeRequest request, @RequestHeader Map<String, String> headers);
}
