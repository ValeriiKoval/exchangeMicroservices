package com.microservice.orderservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShareResponse {

    private Long id;
    private String companySymbol;
    private Long amount;
}
