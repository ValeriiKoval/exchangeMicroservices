package com.microservice.orderservice.external.client;

import com.microservice.orderservice.config.FeignConfig;
import com.microservice.orderservice.payload.response.CompanyInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "EXCHANGE-SERVICE/exchange", configuration = FeignConfig.class)
public interface ExchangeService {

    @GetMapping("/company/{symbol}")
    CompanyInfoResponse getCompanyInfo(@PathVariable String symbol);

}
