package com.microservice.orderservice.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Message {
    private String subject;
    private Map<String, Object> args;
    private TemplateEnum template;
}
