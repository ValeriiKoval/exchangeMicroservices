package com.microservice.orderservice.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class MailDto {

    private Set<String> to;
    private String subject;
    private String body;
}
