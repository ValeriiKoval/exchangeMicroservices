package com.microservice.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/authServiceFallBack")
    public String authServiceFallback() {
        return "Auth Service is down!";
    }

    @GetMapping("/walletServiceFallBack")
    public String walletServiceFallback() {
        return "Wallet Service is down!";
    }

    @GetMapping("/exchangeServiceFallBack")
    public String exchangeServiceFallback() {
        return "Exchange Service is down!";
    }

    @GetMapping("/orderServiceFallBack")
    public String orderServiceFallback() {
        return "Order Service is down!";
    }

}
