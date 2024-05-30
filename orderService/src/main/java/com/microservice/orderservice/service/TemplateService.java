package com.microservice.orderservice.service;

import com.microservice.orderservice.model.TemplateEnum;

import java.util.Map;

public interface TemplateService {
    String defineBody(TemplateEnum templateEnum, Map<String, Object> args);
}
