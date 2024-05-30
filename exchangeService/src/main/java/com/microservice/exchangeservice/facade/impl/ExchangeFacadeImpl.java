package com.microservice.exchangeservice.facade.impl;

import com.microservice.exchangeservice.exception.ResourceNotFoundException;
import com.microservice.exchangeservice.external.client.ExchangeService;
import com.microservice.exchangeservice.facade.ExchangeFacade;
import com.microservice.exchangeservice.payload.response.CompanyInfoResponse;
import com.microservice.exchangeservice.payload.response.StockInfoResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeFacadeImpl implements ExchangeFacade {

    private final ExchangeService exchangeService;

    public ExchangeFacadeImpl(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @Override
    public CompanyInfoResponse getCompanyInfo(final String symbol) {
        final List<CompanyInfoResponse> companyProfiles = exchangeService.getCompanyProfile(symbol);
        if (companyProfiles.isEmpty()) {
            throw new ResourceNotFoundException("Company with symbol " + symbol + " not found");
        }
        return companyProfiles.get(0);
    }

    @Override
    public List<StockInfoResponse> getStockList(final String search) {
        final List<StockInfoResponse> stockList = exchangeService.getStockList();
        if (StringUtils.isNotBlank(search)) {
            return stockList.stream()
                    .filter(stockInfoResponse -> stockInfoResponse.getSymbol().contains(search))
                    .toList();
        }
        return stockList;
    }
}
