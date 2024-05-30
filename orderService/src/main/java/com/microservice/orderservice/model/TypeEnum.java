package com.microservice.orderservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TypeEnum {
    BUY("Buy"),
    SELL("Sell");

    private final String name;
}
