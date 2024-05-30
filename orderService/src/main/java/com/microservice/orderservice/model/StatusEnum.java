package com.microservice.orderservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusEnum {
    IN_PROGRESS("In progress"),
    SUCCESS("Success"),
    FAILED("Failed"),
    CANCELED("Canceled");

    private final String name;
}
