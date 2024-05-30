package com.microservice.model;

import lombok.Data;

import java.util.Set;

@Data
public class Mail {
    private Set<String> to;
    private String subject;
    private String body;
}