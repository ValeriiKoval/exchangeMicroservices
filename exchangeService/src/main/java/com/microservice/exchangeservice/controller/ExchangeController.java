package com.microservice.exchangeservice.controller;

import com.microservice.exchangeservice.facade.ExchangeFacade;
import com.microservice.exchangeservice.payload.response.CompanyInfoResponse;
import com.microservice.exchangeservice.payload.response.StockInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeFacade exchangeFacade;

    @GetMapping("/stock/list")
    public List<StockInfoResponse> getStockList(@RequestParam(required = false) final String search) {
        return exchangeFacade.getStockList(search);
    }

    @GetMapping("/company/{symbol}")
    public CompanyInfoResponse getCompanyInfo(@PathVariable final String symbol) {
        return exchangeFacade.getCompanyInfo(symbol);
    }
}
