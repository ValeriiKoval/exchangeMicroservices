package com.microservice.orderservice.external.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.orderservice.exception.CustomException;
import com.microservice.orderservice.exception.error.ApiError;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper
                = new ObjectMapper();

        log.info("response url::{}", response.request().url());
        log.info("response headers::{}", response.request().headers());

        try {
            final ApiError errorResponse
                    = objectMapper.readValue(response.body().asInputStream(),
                    ApiError.class);

            return new CustomException(errorResponse.getMessage(),
                    errorResponse.getStatus().getReasonPhrase(),
                    response.status());

        } catch (IOException e) {
            throw new CustomException(e.getMessage(),
                    "INTERNAL_SERVER_ERROR",
                    500);
        }
    }
}
