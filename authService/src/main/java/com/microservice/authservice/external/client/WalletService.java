package com.microservice.authservice.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "WALLET-SERVICE/wallet")
public interface WalletService {

    @PostMapping
    ResponseEntity<Void> createWallet(@RequestHeader Map<String, String> headers);
}
