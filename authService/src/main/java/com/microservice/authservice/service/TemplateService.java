package com.microservice.authservice.service;

import com.microservice.authservice.model.TemplateEnum;

import java.util.Map;

public interface TemplateService {
    String defineBody(TemplateEnum templateEnum, Map<String, Object> args);
}
