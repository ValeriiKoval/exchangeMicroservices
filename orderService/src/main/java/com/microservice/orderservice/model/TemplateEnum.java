package com.microservice.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TemplateEnum {

    SUCCESSFUL_ORDER("orderFinalStatus.ftl"),
    FAILED_ORDER("orderFinalStatus.ftl");

    private final String name;
}