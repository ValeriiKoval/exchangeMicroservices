package com.microservice.exchangeservice.external.client;

import com.microservice.exchangeservice.payload.response.CompanyInfoResponse;
import com.microservice.exchangeservice.payload.response.StockInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "external-exchange", url = "${external.exchange.url}")
public interface ExchangeService {

    @GetMapping("/profile/{symbol}")
    List<CompanyInfoResponse> getCompanyProfile(@PathVariable String symbol);

    @GetMapping("/stock/list")
    List<StockInfoResponse> getStockList();
}
