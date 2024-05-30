package com.microservice.orderservice.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyInfoResponse {

    private String symbol;
    private String exchange;
    private String exchangeShortName;
    private Double price;
    private String companyName;
    private String description;
    private String website;
}
