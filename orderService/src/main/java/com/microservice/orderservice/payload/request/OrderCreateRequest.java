package com.microservice.orderservice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequest {

    @NotNull
    private String companySymbol;
    @NotNull
    @Positive
    private Double price;
    @NotNull
    @Positive
    private Long amount;
    @NotNull
    private String type;
}
