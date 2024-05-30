package com.microservice.exchangeservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockInfoResponse {

    private String symbol;
    private String exchange;
    private String exchangeShortName;
    private Double price;
    private String name;
}
