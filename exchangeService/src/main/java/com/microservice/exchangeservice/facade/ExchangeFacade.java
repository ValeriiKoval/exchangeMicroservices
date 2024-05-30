package com.microservice.exchangeservice.facade;

import com.microservice.exchangeservice.payload.response.CompanyInfoResponse;
import com.microservice.exchangeservice.payload.response.StockInfoResponse;

import java.util.List;

public interface ExchangeFacade {
    CompanyInfoResponse getCompanyInfo(String symbol);

    List<StockInfoResponse> getStockList(String search);
}
